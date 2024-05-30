package cn.rentaotao.gateway.config.filter;

import cn.rentaotao.gateway.service.AuthService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.HasRouteId;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 鉴权认证
 *
 * @author rtt
 * @date 2024/5/30 14:05
 */
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    // 权限处理类
    private final AuthService authService;

    public AuthGatewayFilterFactory(AuthService authService) {
        super(Config.class);
        this.authService = authService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        // 通过 authService 校验 Request 的 token
        return new GatewayFilter() {

            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                return null;
            }
        };
    }

    public static class Config implements HasRouteId {

        private String routeId;

        @Getter
        @Setter
        private String Authentication;

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
