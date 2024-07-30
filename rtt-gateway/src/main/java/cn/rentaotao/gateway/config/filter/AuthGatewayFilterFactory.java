package cn.rentaotao.gateway.config.filter;

import cn.rentaotao.gateway.service.AuthService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.HasRouteId;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.setResponseStatus;

/**
 * 鉴权认证
 *
 * @author rtt
 * @date 2024/5/30 14:05
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties("spring.cloud.gateway.filter.auth")
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    // 权限处理类
    private final AuthService authService;

    @Getter
    @Setter
    private boolean enable = false;

    public AuthGatewayFilterFactory(AuthService authService) {
        super(Config.class);
        this.authService = authService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        // 通过 authService 校验 Request 的 token
        return (exchange, chain) -> {
            if (!enable) {
                return chain.filter(exchange);
            }
            ServerHttpRequest request = exchange.getRequest();
            MultiValueMap<String, String> queryParams = request.getQueryParams();
            List<String> auths = queryParams.get(config.getHeaderKey());
            if (auths ==null || auths.isEmpty()) {
                setResponseStatus(exchange, HttpStatus.NOT_FOUND);
                return exchange.getResponse().setComplete();
            }
            return authService.isAllowed(config.routeId, auths.get(0)).flatMap(result -> {
                if (result) {
                    return chain.filter(exchange);
                }

                setResponseStatus(exchange, HttpStatus.NOT_FOUND);
                return exchange.getResponse().setComplete();
            });
        };
    }

    public static class Config implements HasRouteId {

        private String routeId;

        @Getter
        @Setter
        private String headerKey;

        @Override
        public void setRouteId(String routeId) {
            this.routeId = routeId;
        }

        @Override
        public String getRouteId() {
            return this.routeId;
        }

    }
}
