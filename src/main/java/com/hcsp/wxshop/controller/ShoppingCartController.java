package com.hcsp.wxshop.controller;

import com.hcsp.wxshop.entity.HttpException;
import com.hcsp.wxshop.entity.PageResponse;
import com.hcsp.wxshop.entity.Response;
import com.hcsp.wxshop.entity.ShoppingCartData;
import com.hcsp.wxshop.service.ShoppingCartService;
import com.hcsp.wxshop.service.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(
            ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /**
     *
     * @param request 加购物车请求
     * @return 添加后的结果
     */
    @PostMapping("/shoppingCart")
    public Response<ShoppingCartData> addToShoppingCart(@RequestBody AddToShoppingCartRequest request) {
        try {
            return Response.of(shoppingCartService.addToShoppingCart(request));
        } catch (HttpException e) {
            return Response.of(e.getMessge(), null);
        }
    }

    public static class AddToShoppingCartRequest {
        List<AddToShoppingCartItem> goods;

        public List<AddToShoppingCartItem> getGoods() {
            return goods;
        }

        public void setGoods(List<AddToShoppingCartItem> goods) {
            this.goods = goods;
        }
    }

    public static class AddToShoppingCartItem {
        long id;
        int number;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    @GetMapping("/shoppingCart")
    public PageResponse<ShoppingCartData> getShoppingCart(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize
    ) {
        return shoppingCartService.getShoppingCartOfUser(UserContext.getCurrentUser().getId(),
                pageNum,
                pageSize);
    }
}
