package cn.rentaotao.gateway.config.filter;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.ratelimit.AbstractRateLimiter;
import org.springframework.cloud.gateway.support.ConfigurationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

/**
 * {@link org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory}
 * {@link org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter}
 *
 * @author rtt
 * @date 2024/5/30 11:12
 */
@ConfigurationProperties(prefix = "spring.cloud.gateway.local-memory-rate-limiter")
public class LocalMemoryRateLimiter extends AbstractRateLimiter<LocalMemoryRateLimiter.Config> implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    protected LocalMemoryRateLimiter(Class<Config> configClass, String configurationPropertyName, ConfigurationService configurationService) {
        super(configClass, configurationPropertyName, configurationService);
    }

    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Validated
    public static class Config {

    }
}
