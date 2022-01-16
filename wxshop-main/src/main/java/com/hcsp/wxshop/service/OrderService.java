package com.hcsp.wxshop.service;

import com.hcsp.api.data.GoodsInfo;
import com.hcsp.api.data.OrderInfo;
import com.hcsp.api.generate.Order;
import com.hcsp.api.rpc.OrderRpcService;
import com.hcsp.wxshop.dao.GoodsStockMapper;
import com.hcsp.wxshop.entity.GoodsWithNumber;
import com.hcsp.wxshop.entity.HttpException;
import com.hcsp.wxshop.entity.OrderResponse;
import com.hcsp.wxshop.generate.Goods;
import com.hcsp.wxshop.generate.GoodsMapper;
import com.hcsp.wxshop.generate.ShopMapper;
import com.hcsp.wxshop.generate.UserMapper;
import jdk.nashorn.internal.runtime.Context;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    @Reference(version = "${wxshop.orderservice.version}")
    private OrderRpcService orderRpcService;

    private UserMapper userMapper;

    private GoodsStockMapper goodsStockMapper;

    private GoodsService goodsService;

    private ShopMapper shopMapper;

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public OrderService(UserMapper userMapper,
                        GoodsStockMapper goodsStockMapper,
                        ShopMapper shopMapper,
                        GoodsService goodsService) {
        this.userMapper = userMapper;
        this.shopMapper = shopMapper;
        this.goodsService = goodsService;
        this.goodsStockMapper = goodsStockMapper;
    }

    public OrderResponse createOrder(OrderInfo orderInfo, Long userId) {
        if (!deductStock(orderInfo)) {
            throw HttpException.gone("扣减库存失败！");
        }
        Map<Long, Goods> idToGoodsMap = getIdToGoodsMap(orderInfo);
        Order createdOrder = createOrderViaRpc(orderInfo, userId, idToGoodsMap);
        return generateResponse(createdOrder, idToGoodsMap, orderInfo);
    }

    private OrderResponse generateResponse(Order createdOrder, Map<Long, Goods> idToGoodsMap, OrderInfo orderInfo) {
        OrderResponse response = new OrderResponse(createdOrder);

        Long shopId = new ArrayList<>(idToGoodsMap.values()).get(0).getShopId();
        response.setShop(shopMapper.selectByPrimaryKey(shopId));
        response.setGoods(
                orderInfo.getGoods()
                        .stream()
                        .map(goods -> toGoodsWithNumber(goods, idToGoodsMap))
                        .collect(toList())
        );
        return response;
    }

    private Map<Long, Goods> getIdToGoodsMap(OrderInfo orderInfo) {
        List<Long> goodsId = orderInfo.getGoods()
                .stream()
                .map(GoodsInfo::getId)
                .collect(toList());
        return goodsService.getIdToGoodsMap(goodsId);
    }

    private Order createOrderViaRpc(OrderInfo orderInfo, Long userId, Map<Long, Goods> idToGoodsMap) {
        Order order = new Order();
        order.setUserId(userId);
        order.setAddress(userMapper.selectByPrimaryKey(userId).getAddress());
        order.setTotalPrice(calculateTotalPrice(orderInfo, idToGoodsMap));
        return orderRpcService.createOrder(orderInfo, order);
    }

    /**
     * 扣减库存
     *
     * @param orderInfo
     * @return 若全部扣减成功，返回true，否则返回false
     */
    private boolean deductStock(OrderInfo orderInfo) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(false)) {
            // 事情
            for (GoodsInfo goodsInfo : orderInfo.getGoods()) {
                if (goodsStockMapper.deductStock(goodsInfo) <= 0) {
                    LOGGER.error("扣减库存失败，商品id：" + goodsInfo.getId() + ",数量：" + goodsInfo.getNumber());
                    sqlSession.rollback();
                    return false;
                }
            }
            // 做完之后手动提交
            sqlSession.commit();
            return true;
        }
    }

    private GoodsWithNumber toGoodsWithNumber(GoodsInfo goodsInfo, Map<Long, Goods> idToGoodsMap) {
        GoodsWithNumber ret = new GoodsWithNumber(idToGoodsMap.get(goodsInfo.getId()));
        ret.setNumber(goodsInfo.getNumber());
        return ret;
    }

    private BigDecimal calculateTotalPrice(OrderInfo orderInfo, Map<Long, Goods> idToGoodsMap) {
        BigDecimal result = BigDecimal.ZERO;

        for (GoodsInfo goodsInfo : orderInfo.getGoods()) {
            Goods goods = idToGoodsMap.get(goodsInfo.getId());
            if (goods == null) {
                throw HttpException.badRequest("goods id非法：" + goodsInfo.getId());
            }
            if (goodsInfo.getNumber() <= 0) {
                throw HttpException.badRequest("number非法：" + goodsInfo.getNumber());
            }

            result = result.add(goods.getPrice().multiply(new BigDecimal(goodsInfo.getNumber())));
        }
        return result;
    }
}
