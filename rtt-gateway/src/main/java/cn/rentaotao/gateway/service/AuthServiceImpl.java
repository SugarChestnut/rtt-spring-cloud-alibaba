package cn.rentaotao.gateway.service;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.FilterArgsEvent;
import org.springframework.cloud.gateway.support.AbstractStatefulConfigurable;
import org.springframework.cloud.gateway.support.ConfigurationService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author rtt
 * @date 2024/5/30 14:15
 */
@Service
public class AuthServiceImpl extends AbstractStatefulConfigurable<AuthServiceImpl.Config> implements AuthService, ApplicationListener<FilterArgsEvent> {

    private static final String CONFIGURATION_PROPERTY_NAME = "auth";

    private final ConfigurationService configurationService;

    private Config deaultConfig;

    @Autowired
    public AuthServiceImpl(ConfigurationService configurationService) {
        super(Config.class);
        this.configurationService = configurationService;
    }

    @Override
    public void onApplicationEvent(FilterArgsEvent event) {
        Map<String, Object> args = event.getArgs();

        if (args.isEmpty() || !hasRelevantKey(args)) {
            return;
        }

        String routeId = event.getRouteId();

        Config routeConfig = BeanUtils.instantiateClass(getConfigClass());

        if (this.configurationService != null) {
            this.configurationService.with(routeConfig).name(CONFIGURATION_PROPERTY_NAME).normalizedProperties(args)
                    .bind();
        }

        getConfig().put(routeId, routeConfig);
    }

    private boolean hasRelevantKey(Map<String, Object> args) {
        return args.keySet().stream().anyMatch(key -> key.startsWith(CONFIGURATION_PROPERTY_NAME + "."));
    }

    @Override
    public Mono<Boolean> isAllowed(String routeId, String userKey) {
        System.out.println("routeId: " + routeId + ", userKey: " + userKey);
        Config config = getConfig().get(routeId);
        if (config != null && config.getAllPurpose().equals(userKey)) {
            return Mono.just(true);
        }
        // 查询用户信息

        return Mono.just("rtt".equals(userKey));
    }

    @Validated
    @Data
    public static class Config {

        private String allPurpose;
    }
}
