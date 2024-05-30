package cn.rentaotao.order.web;

import cn.rentaotao.api.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author rtt
 * @date 2023/9/6 16:04
 */
@RequestMapping("/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("/create")
    public String createOrder(@RequestBody Map<String, Object> data) {
        orderService.create((Integer) data.get("userId"), (Integer) data.get("fee"));
        return "success";
    }
}
