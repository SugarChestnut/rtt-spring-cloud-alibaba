package cn.rentaotao.gateway.config.filter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

/**
 * 替换默认的 KeyResolver
 *
 * @author rtt
 * @date 2024/5/30 13:22
 */
@Component
public class IpAddressKeyResolver implements KeyResolver {

    /**
     * 获取 rateLimiter 需要的客户端标识，用 ip 来标识
     *
     * @param exchange
     * @return
     */
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        // 在 limiter 中可以配置 拒绝空的 key
        if (remoteAddress == null) {
            return Mono.just("");
        }
        return Mono.just(remoteAddress.getAddress().getHostAddress());
    }
}
