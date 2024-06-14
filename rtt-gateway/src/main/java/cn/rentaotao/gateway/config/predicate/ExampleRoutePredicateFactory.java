package cn.rentaotao.gateway.config.predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

/**
 * @author rtt
 * @date 2024/6/5 16:51
 */
public class ExampleRoutePredicateFactory extends AbstractRoutePredicateFactory<ExampleRoutePredicateFactory.Config> {

    public ExampleRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return serverWebExchange -> {
            ServerHttpRequest request = serverWebExchange.getRequest();
            return matches(config, request);
        };
    }

    private boolean matches(Config config, ServerHttpRequest request) {
        return false;
    }

    public static class Config {

    }
}
