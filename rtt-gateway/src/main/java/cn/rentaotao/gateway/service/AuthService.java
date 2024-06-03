package cn.rentaotao.gateway.service;

import reactor.core.publisher.Mono;

/**
 * @author rtt
 * @date 2024/5/30 14:15
 */
public interface AuthService {

    Mono<Boolean> isAllowed(String routeId, String userKey);
}
