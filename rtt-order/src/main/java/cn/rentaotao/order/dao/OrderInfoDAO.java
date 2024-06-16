package cn.rentaotao.order.dao;

import cn.rentaotao.core.dao.BaseDAO;
import cn.rentaotao.order.domain.entity.OrderInfo;
import cn.rentaotao.order.mapper.OrderInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OrderInfoDAO extends BaseDAO<OrderInfoMapper, OrderInfo> {
}
