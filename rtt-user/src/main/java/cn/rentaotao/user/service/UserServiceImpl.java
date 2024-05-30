package cn.rentaotao.user.service;

import cn.rentaotao.api.service.user.UserService;
import cn.rentaotao.user.mapper.UserMapper;
import cn.rentaotao.user.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rtt
 * @date 2023/8/18 10:41
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deduct(Integer id, Integer fee) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.setEntity(new User(id));
        User user = userMapper.selectOne(wrapper);
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
