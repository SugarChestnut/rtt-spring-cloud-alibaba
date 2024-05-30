package cn.rentaotao.user;

import cn.rentaotao.api.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RttUserApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        userService.deduct(1, 1);
    }

}
