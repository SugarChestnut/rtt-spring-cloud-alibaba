package cn.rentaotao.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RttUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(RttUserApplication.class, args);
    }

}
