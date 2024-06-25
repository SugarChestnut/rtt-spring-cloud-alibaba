package cn.rentaotao.product;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bootstrap.BootstrapConfiguration;

@SpringBootApplication
@EnableDubbo
@BootstrapConfiguration
public class RttProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(RttProductApplication.class, args);
    }

}
