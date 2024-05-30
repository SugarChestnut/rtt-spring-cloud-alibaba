package cn.rentaotao.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
//@EnableConfigurationProperties(LocalMemoryRateLimiter.class)
@ConfigurationPropertiesScan
public class RttGatewayApplication {

    public static void main(String[] args)  {
        SpringApplication.run(RttGatewayApplication.class, args);
    }

}
