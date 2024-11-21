package cn.rentaotao.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

/**
 * @author rtt
 * @date 2024/11/11 15:47
 */
@SpringBootApplication
@EnableWebSocketMessageBroker
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
