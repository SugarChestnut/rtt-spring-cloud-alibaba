package cn.rentaotao.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class RttOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RttOrderApplication.class, args);
    }

}
