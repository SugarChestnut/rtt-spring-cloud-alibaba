package cn.rentaotao.server.service;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rtt
 * @date 2023/9/6 16:21
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {

    /**
     * 简单使用 http 访问其他服务
     */
    private final RestTemplate restTemplate;

    @Autowired
    public PurchaseServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 分布式事务测试，下单包含金额扣件，订单生成
     *
     * @param userId 用户ID
     * @param fee 费用
     */
    @GlobalTransactional
    @Override
    public void create(Integer userId, Integer fee) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        request.put("fee", fee);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> mapHttpEntity = new HttpEntity<>(request, header);

        String orderResult = restTemplate.postForObject("http://rtt-order/order/create", mapHttpEntity, String.class);
        if (!"success".equals(orderResult)) {
            throw new RuntimeException("订单创建失败");
        }
        String feeResult = restTemplate.postForObject("http://rtt-user/user/deduct", mapHttpEntity, String.class);
        if (!"success".equals(feeResult)) {
            throw new RuntimeException("扣费失败");
        }
    }
}
