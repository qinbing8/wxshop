package com.hcsp.wxshop.controller;

import com.hcsp.api.data.OrderInfo;
import com.hcsp.wxshop.entity.OrderResponse;
import com.hcsp.wxshop.entity.Response;
import com.hcsp.wxshop.service.OrderService;
import com.hcsp.wxshop.service.UserContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private OrderService orderService;

//    @RequestMapping("/testRpc")
//    public String testRpc() {
//        orderService.sayHello("123");
//        return "";
//    }

    @PostMapping("/order")
    public Response<OrderResponse> createOrder(@RequestBody OrderInfo orderInfo) {
        return Response.of(orderService.createOrder(orderInfo, UserContext.getCurrentUser().getId()));

    }
}
