package cn.rentaotao.order;

import cn.rentaotao.api.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RttOrderApplicationTests {

    @Autowired
    OrderService orderService;

    @Test
    void contextLoads() {
        orderService.create(1, 100);
    }

}
