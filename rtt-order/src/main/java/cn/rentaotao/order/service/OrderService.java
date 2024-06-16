package cn.rentaotao.order.service;

import cn.rentaotao.order.domain.dto.CreateOrderDTO;
import cn.rentaotao.order.domain.dto.GenOrderIdDTO;
import cn.rentaotao.order.domain.request.CreateOrderRequest;
import cn.rentaotao.order.domain.request.GenOrderIdRequest;

public interface OrderService {

    GenOrderIdDTO genOrderNo(GenOrderIdRequest genOrderIdRequest);

    CreateOrderDTO createOrder(CreateOrderRequest createOrderRequest);
}
