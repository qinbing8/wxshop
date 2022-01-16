package com.hcsp.wxshop.mock;

import com.hcsp.api.rpc.OrderRpcService;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "${wxshop.orderservice.version}")
public class MockOrderService implements OrderRpcService {
    @Override
    public String sayHello(String name) {
        return "I'm com.hcsp.wxshop.mock";
    }
}
