package cn.rentaotao.server.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

/**
 * @author rtt
 * @date 2023/8/18 13:10
 */
@Configuration
public class CloudConfig {

    /**
     * sentinel 在 @SentinelRestTemplate 中指定限流和降级的策略
     * blockHandler 处理限流策略
     * fallback 处理降级策略
     *
     * @return RestTemplate
     */
    @Bean
    @SentinelRestTemplate(
            fallback = "handleException",
            fallbackClass = ExceptionUtils.class,
            urlCleaner = "urlClean",
            urlCleanerClass = CloudConfig.class
    )
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static String urlClean(String origin) {
        if (origin.contains("GET:http://rtt-user/user/find")) {
            return "GET:http://rtt-user/user/find";
        }
        return origin;
    }

    @Bean
    public ClientHttpRequestInterceptor rttRestTemplateInterceptor() {
        return new RttRestTemplateInterceptor();
    }

}
