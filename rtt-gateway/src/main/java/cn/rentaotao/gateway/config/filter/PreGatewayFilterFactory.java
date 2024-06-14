package cn.rentaotao.gateway.config.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author rtt
 * @date 2024/6/6 09:16
 */
public class PreGatewayFilterFactory extends AbstractGatewayFilterFactory<PreGatewayFilterFactory.Config> {

    public PreGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest.Builder mutate = exchange.getRequest().mutate();
            return chain.filter(exchange.mutate().request(mutate.build()).build());
        };
    }

    public static class Config {
        // Put the configuration properties for you filter here.
    }
}
