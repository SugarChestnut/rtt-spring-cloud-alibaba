package cn.rentaotao.order.mapper;

import cn.rentaotao.order.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author rtt
 * @date 2023/9/6 15:52
 */
@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {
}
