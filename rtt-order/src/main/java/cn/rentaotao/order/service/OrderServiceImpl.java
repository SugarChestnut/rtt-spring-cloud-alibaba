package cn.rentaotao.order.service;

import cn.rentaotao.api.service.order.OrderService;
import cn.rentaotao.order.mapper.OrderMapper;
import cn.rentaotao.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rtt
 * @date 2023/9/6 15:55
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Transactional
    @Override
    public void create(Integer userId, Integer amount) {
        Order order = new Order();
        order.setUserId(userId);
        order.setAmount(amount);
        orderMapper.insert(order);
    }
}
