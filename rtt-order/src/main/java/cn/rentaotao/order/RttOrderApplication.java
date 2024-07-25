package cn.rentaotao.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// 启用 dubbo 服务
@EnableDubbo
// 可以与 dubbo 共存
@EnableFeignClients
public class RttOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RttOrderApplication.class, args);
    }

}
