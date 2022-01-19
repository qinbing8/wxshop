package com.hcsp.wxshop.controller;

import com.hcsp.api.data.OrderInfo;
import com.hcsp.wxshop.entity.HttpException;
import com.hcsp.wxshop.entity.OrderResponse;
import com.hcsp.wxshop.entity.Response;
import com.hcsp.wxshop.service.OrderService;
import com.hcsp.wxshop.service.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * @param orderInfo
     * @param response
     * @return 响应
     */
    @PostMapping("/order")
    public Response<OrderResponse> createOrder(@RequestBody OrderInfo orderInfo, HttpServletResponse response) {
        try {
            orderService.deductStock(orderInfo);
            return Response.of(orderService.createOrder(orderInfo, UserContext.getCurrentUser().getId()));
        } catch (HttpException e) {
            response.setStatus(e.getStatusCode());
            return Response.of(e.getMessage(), null);
        }
    }
}
