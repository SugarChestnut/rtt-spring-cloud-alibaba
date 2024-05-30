package cn.rentaotao.user.mapper;

import cn.rentaotao.user.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author rtt
 * @date 2023/9/5 15:18
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
}
