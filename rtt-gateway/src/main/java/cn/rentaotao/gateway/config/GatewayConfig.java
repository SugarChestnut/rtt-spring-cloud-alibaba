package cn.rentaotao.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ipresolver.RemoteAddressResolver;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author rtt
 * @date 2023/8/29 16:19
 */
@Configuration
public class GatewayConfig {

    /**
     * 代码方式构建 route
     */
//    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, RemoteAddressResolver remoteAddressResolver) {
        // RemoteAddrRoutePredicate
        return builder.routes()
                .route(
                        "direct-route",
                        r -> r.remoteAddr("10.1.1.1", "10.10.1.1/24").uri("http://127.0.0.1:13142")
                )
                .route(
                        "proxied-route",
                        r -> r.remoteAddr(remoteAddressResolver, "10.10.1.1", "10.10.1.1/24").uri("http://127.0.0.1:13142")
                )
                .build();
    }

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> exchange.getPrincipal()
                .map(Principal::getName)
                .defaultIfEmpty("DEFAULT_USER")
                .map(username -> {
                    exchange.getRequest().mutate().header("CUSTOM_REQUEST_HEADER", username).build();
                    return exchange;
                })
                .flatMap(chain::filter);
    }

    @Bean
    public GlobalFilter postCustomGlobalFilter() {
        return (exchange, chain) -> chain.filter(exchange)
                .then(Mono.just(exchange))
                .map(serverWebExchange -> {
                    serverWebExchange.getResponse()
                            .getHeaders()
                            .set("CUSTOM_RESPONSE_HEADER", HttpStatus.OK.equals(serverWebExchange.getResponse().getStatusCode()) ? "It worked" : "It did not worked");
                    return serverWebExchange;
                })
                .then();
    }

    /**
     * RemoteAddrRoutePredicateFactory
     * 解决因为代理服务器的原因，header 属性 X-Forwarded-For 存在多个地址
     */
    @Bean
    public RemoteAddressResolver remoteAddressResolver() {
        return XForwardedRemoteAddressResolver.maxTrustedIndex(1);
    }

    /**
     * Gateway 整合 Sentinel
     *
     * @return 全局过滤器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    /**
     * 限流异常处理器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler(
            ObjectProvider<List<ViewResolver>> viewResolvers, ServerCodecConfigurer serverCodecConfigurer) {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers.getIfAvailable(Collections::emptyList), serverCodecConfigurer);
    }

    @PostConstruct
    public void init() {
        // 初始化限流规则
        initGatewayRules();
//        initCustomizedApis();
    }

    /**
     * Route 维度限流
     */
    private void initGatewayRules() {
        HashSet<GatewayFlowRule> rules = new HashSet<>();
        rules.add(new GatewayFlowRule("rtt-server-route").setCount(1).setIntervalSec(1));
        GatewayRuleManager.loadRules(rules);
    }

    /**
     * 自定义 API 分组限流
     */
    private void initCustomizedApis() {
        HashSet<ApiDefinition> apiDefinitions = new HashSet<>();
        /*
            都是资源
         */
        ApiDefinition apiDefinition = new ApiDefinition("first-customized-api");
        apiDefinition.setPredicateItems(new HashSet<>() {{
            add(new ApiPathPredicateItem().setPattern("/first/**"));
            add(new ApiPathPredicateItem().setPattern("/second/**").setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
        }});
        apiDefinitions.add(apiDefinition);
        GatewayApiDefinitionManager.loadApiDefinitions(apiDefinitions);
    }

    /**
     * 自定义web 异常处理器
     *
     * @return WebExceptionHandler
     */
    @Bean
    public WebExceptionHandler rttSentinelGatewayBlockExceptionHandler() {
        return new WebExceptionHandler() {
            @Override
            public @NonNull Mono<Void> handle(@NonNull ServerWebExchange exchange, @NonNull Throwable ex) {
                // 通过判断 ex 类型，处理异常
                if (ex instanceof AuthException) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                    byte[] data = "{\"code\": -1, \"msg\": \"认证不通过\"}".getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = response.bufferFactory().wrap(data);
                    return response.writeWith(Mono.just(buffer));
                } else {
                    return Mono.error(ex);
                }
            }
        };
    }
}
