package cn.rentaotao.product;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.bootstrap.BootstrapConfiguration;

@SpringBootApplication
// 启用 dubbo
@EnableDubbo
// 扫描 @ConfigurationProperties 类，而不需要再添加 @Configuration
@ConfigurationPropertiesScan
public class RttProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(RttProductApplication.class, args);
    }

}
