package cn.rentaotao.order.web;

import cn.rentaotao.core.bean.JsonResult;
import cn.rentaotao.order.domain.dto.CreateOrderDTO;
import cn.rentaotao.order.domain.request.CreateOrderRequest;
import cn.rentaotao.order.service.OrderService;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 注入 Nacos 的 Naming 服务
     */
    @NacosInjected
    private NamingService namingService;

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
    @RequestMapping("/create")
    public JsonResult<CreateOrderDTO> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {

        return JsonResult.buildSuccess(orderService.createOrder(createOrderRequest));
    }
}
