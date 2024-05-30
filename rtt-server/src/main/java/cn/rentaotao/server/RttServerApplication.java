package cn.rentaotao.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RttServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RttServerApplication.class, args);
    }

}
