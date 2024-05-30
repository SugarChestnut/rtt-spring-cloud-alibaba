package cn.rentaotao.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ipresolver.RemoteAddressResolver;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * @author rtt
 * @date 2023/8/29 16:19
 */
@Configuration
public class GatewayConfiguration {

    //    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        RemoteAddressResolver resolver = XForwardedRemoteAddressResolver
                .maxTrustedIndex(1);

        return builder.routes()
                .route(
                        "direct-route",
                        r -> r.remoteAddr("10.1.1.1", "10.10.1.1/24").uri("http://127.0.0.1:13142")
                )
                .route(
                        "proxied-route",
                        r -> r.remoteAddr(resolver, "10.10.1.1", "10.10.1.1/24").uri("http://127.0.0.1:13142")
                )
                .build();
    }

    /**
     * 自动过滤所有的 route
     *
     * @return GlobalFilter
     */
    @Bean
    public GlobalFilter globalFilter() {

        return new GlobalFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("Pre global");
                return chain.filter(exchange).then(Mono.fromRunnable((Runnable) () -> System.out.println("Post Global")));
            }
        };
    }

    /**
     * Gateway 整合 Sentinel
     *
     * @return 全局过滤器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler(
//            List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer) {
//        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
//    }

    @PostConstruct
    public void init() {
        HashSet<GatewayFlowRule> rules = new HashSet<>();
        rules.add(new GatewayFlowRule("request-rate-limiter-route").setCount(1).setIntervalSec(1));
        GatewayRuleManager.loadRules(rules);
    }

    /**
     * web 异常处理器
     *
     * @return WebExceptionHandler
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public WebExceptionHandler rttSentinelGatewayBlockExceptionHandler() {
        return new WebExceptionHandler() {
            @Override
            public @NonNull Mono<Void> handle(@NonNull ServerWebExchange exchange, @NonNull Throwable ex) {
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                byte[] data = "{\"code\": 999, \"msg\": \"访问人数过多\"}".getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(data);
                return response.writeWith(Mono.just(buffer));
            }
        };
    }
}
