package cn.rentaotao.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class RttServerApplicationTests {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PurchaseService purchaseService;

    @Test
    void contextLoads() {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", 1);
        request.put("fee", 100);
        HttpEntity<Map<String, Object>> mapHttpEntity = new HttpEntity<>(request);
        System.out.println(restTemplate.postForObject("http://rtt-user/user/deduct", mapHttpEntity, String.class));
    }

    @Test
    void test1() {
        purchaseService.create(1, 100);
    }

}
