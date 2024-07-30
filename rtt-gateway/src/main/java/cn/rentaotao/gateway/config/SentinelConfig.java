package cn.rentaotao.gateway.config;

import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author rtt
 * @date 2024/7/29 10:13
 */
@Configuration
public class SentinelConfig {

    @Bean("cn.rentaotao.gateway.config.BlockRequestHandler")
    public BlockRequestHandler blockRequestHandler() {

        return (serverWebExchange, throwable) -> ServerResponse.ok()
                .body(Mono.just("{\"code\": -1, \"msg\": \"" + throwable.getMessage() + "\"}"), String.class);
    }
}
