package cn.rentaotao.order.service.impl;

import cn.rentaotao.order.domain.dto.CreateOrderDTO;
import cn.rentaotao.order.domain.dto.GenOrderIdDTO;
import cn.rentaotao.order.domain.request.CreateOrderRequest;
import cn.rentaotao.order.domain.request.GenOrderIdRequest;
import cn.rentaotao.order.service.OrderService;
import cn.rentaotao.product.api.ProductQueryService;
import cn.rentaotao.product.domain.dto.ProductInfoDTO;
import cn.rentaotao.product.domain.dto.ProductQueryDTO;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rtt
 * @date 2023/9/6 15:55
 */
@Service
public class OrderServiceImpl implements OrderService {

    @DubboReference
    private ProductQueryService productQueryService;

    @Override
    public GenOrderIdDTO genOrderNo(GenOrderIdRequest genOrderIdRequest) {
        // TODO 通过数据库生成ID
        return new GenOrderIdDTO();
    }

    @SentinelResource(value = "OrderService#createOrder", entryType = EntryType.IN)
    @Override
    public CreateOrderDTO createOrder(CreateOrderRequest createOrderRequest) {
        // 1、入参检查
        check(createOrderRequest);

        // 2、调用风控服务进行风控检查

        // 3、调用商品服务获取商品信息

        List<CreateOrderRequest.OrderItemRequest> orderItemRequestList = createOrderRequest.getOrderItemRequestList();
        List<ProductQueryDTO> productQueryDTOList = orderItemRequestList.stream()
                .map(orderItemRequest -> orderItemRequest.clone(new ProductQueryDTO()))
                .collect(Collectors.toList());

        List<ProductInfoDTO> productInfoDTOList = productQueryService.query(productQueryDTOList);
        System.out.println(productInfoDTOList.toString());

        // 4、调用营销服务计算订单价格

        // 5、验证订单实付金额

        // 6、调用营销服务锁定优惠券

        // 7、调用库存服务锁定商品库存

        // 8、生成订单到数据库

        // 9、发送订单延迟消息用于支付超时自动关单

        // 返回订单信息
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setOrderId(createOrderRequest.getOrderId());
        return createOrderDTO;
    }

    private void check(CreateOrderRequest createOrderRequest) {

    }

    // 订单请求、商品列表、价格计算结果
    private void newOrder() {

    }
}
