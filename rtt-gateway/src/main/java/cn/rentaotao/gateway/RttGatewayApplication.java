package cn.rentaotao.gateway;

import cn.rentaotao.gateway.config.filter.LocalMemoryRateLimiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties(LocalMemoryRateLimiter.class)
@ConfigurationPropertiesScan
public class RttGatewayApplication {

    public static void main(String[] args)  {
        SpringApplication.run(RttGatewayApplication.class, args);
    }

}
