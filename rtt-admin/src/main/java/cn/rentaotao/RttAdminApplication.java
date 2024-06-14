package cn.rentaotao;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author rtt
 * @date 2024/6/6 14:34
 */
@SpringBootApplication
@EnableAdminServer
public class RttAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(RttAdminApplication.class, args);
    }
}
