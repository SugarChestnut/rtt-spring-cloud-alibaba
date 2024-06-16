package cn.rentaotao.customer.mapper;

import cn.rentaotao.customer.domain.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author rtt
 * @date 2023/9/5 15:18
 */
@Mapper
@Repository
public interface CustomerMapper extends BaseMapper<Customer> {
}
