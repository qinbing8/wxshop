package com.hcsp.wxshop.controller;

import com.hcsp.api.DataStatus;
import com.hcsp.api.data.OrderInfo;
import com.hcsp.api.exceptions.HttpException;
import com.hcsp.wxshop.entity.OrderResponse;
import com.hcsp.wxshop.entity.PageResponse;
import com.hcsp.wxshop.entity.Response;
import com.hcsp.wxshop.service.OrderService;
import com.hcsp.wxshop.service.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @return 响应
     */
    @PostMapping("/order")
    public Response<OrderResponse> createOrder(@RequestBody OrderInfo orderInfo) {
        orderService.deductStock(orderInfo);
        return Response.of(orderService.createOrder(orderInfo, UserContext.getCurrentUser().getId()));
    }

    @DeleteMapping("/order/{id}")
    public Response<OrderResponse> deleteOrder(@PathVariable("id") long orderId) {
        return Response.of(orderService.deleteOrder(orderId, UserContext.getCurrentUser().getId()));
    }

    @GetMapping("/order")
    public PageResponse<OrderResponse> getOrder(@RequestParam("pageNum") Integer pageNum,
                                                @RequestParam("pageSize") Integer pageSize,
                                                @RequestParam(value = "status", required = false) String status) {
        if (status != null && DataStatus.fromStatus(status) == null) {
            throw HttpException.badRequest("非法status: " + status);
        }

        return orderService.getOrder(pageNum, pageSize, DataStatus.valueOf(status));
    }
}
