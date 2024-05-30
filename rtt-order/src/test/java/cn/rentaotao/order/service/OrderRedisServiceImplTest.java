package cn.rentaotao.order.service;

import cn.rentaotao.api.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderRedisServiceImplTest {

    @Autowired
    OrderRedisServiceImpl service;

    @Test
    public void testSet() {
//        service.set();
        service.get();
    }
}
