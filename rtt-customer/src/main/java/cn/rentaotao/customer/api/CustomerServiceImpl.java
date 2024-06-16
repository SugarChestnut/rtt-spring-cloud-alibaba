package cn.rentaotao.customer.api;

import cn.rentaotao.customer.mapper.CustomerMapper;
import cn.rentaotao.customer.domain.Customer;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rtt
 * @date 2023/8/18 10:41
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper userMapper;

    @Autowired
    public CustomerServiceImpl(CustomerMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deduct(Integer id, Integer fee) {
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        Customer customer = new Customer();
        customer.setId(id);
        wrapper.setEntity(customer);
        Customer user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        Integer account = user.getAccount();
        if (account <= fee) {
            throw new RuntimeException("账户余额不足");
        }
        user.setAccount(account - fee);
        userMapper.updateById(user);
    }
}
