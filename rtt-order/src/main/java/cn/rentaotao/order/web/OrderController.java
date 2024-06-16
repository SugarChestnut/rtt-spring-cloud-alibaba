package cn.rentaotao.order.web;

import cn.rentaotao.core.bean.JsonResult;
import cn.rentaotao.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 创建订单ID
     */
    @RequestMapping("/genOrderNo")
    public JsonResult<String> genOrderId() {

        return JsonResult.buildSuccess();
    }

    /**
     * 创建订单
     */
    @RequestMapping("/createOrder")
    public JsonResult<String> createOrder() {

        return JsonResult.buildSuccess();
    }
}
