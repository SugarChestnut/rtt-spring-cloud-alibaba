package cn.rentaotao.gateway.config.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 默认最低顺序
 *
 * @author rtt
 * @date 2024/5/31 13:32
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLogGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Pre log global filer");
        ServerHttpRequest request = exchange.getRequest();
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        if (remoteAddress != null) {
            System.out.println(remoteAddress.getAddress().getHostAddress());
        }
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            System.out.println("Post log global filter");
        }));
    }
}
