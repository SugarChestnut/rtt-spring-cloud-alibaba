package cn.rentaotao.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author rtt
 * @date 2023/9/13 13:30
 */
@Service
public class OrderRedisServiceImpl {

    private final StringRedisTemplate srt;

    @Autowired
    public OrderRedisServiceImpl(StringRedisTemplate srt) {
        this.srt = srt;
    }

    public void get() {
        System.out.println(srt.boundValueOps("rtt").get());
    }

    public void set() {
        srt.boundValueOps("rtt").set("yyl");
    }

    public void setExpire() {

    }

    public void delete() {

    }
}
