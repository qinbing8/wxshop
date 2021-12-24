package com.hcsp.wxshop.mock;

import com.hcsp.wxshop.api.OrderService;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "${wxshop.orderservice.version}")
public class MockOrderService implements OrderService {
    @Override
    public void placeOrder(int goodsId, int number) {
        System.out.println("I'm com.hcsp.wxshop.mock");
    }
}
