package cn.rentaotao.server.config;

import com.alibaba.csp.sentinel.adapter.dubbo.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.UrlCleaner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintWriter;

/**
 * @author rtt
 * @date 2023/8/21 15:52
 */
@Configuration
public class SentinelConfig {

    /**
     * 配置 controller 资源的控制处理
     * 或者也可以使用 @ControllerAdvisor 来处理 BlockException
     *
     * @return BlockExceptionHandler
     */
    @Bean
    public BlockExceptionHandler sentinelBlockExceptionHandler() {
        return (request, response, e) -> {
            // 429 Too Many Requests
            response.setStatus(429);

            PrintWriter out = response.getWriter();
            out.print("Oops, blocked by Sentinel: " + e.getClass().getSimpleName());
            out.flush();
            out.close();
        };
    }

    /**
     * 当 url 中存在 pathParam 的时候，不同的 param 都会被当成一个资源，比如想用户查询，每个用户都是一个资源，
     * 从而导致 sentinel 中资源过多的问题，而且默认情况下资源的数量是有限的，对于多出的资源将不会被保护。
     * 通过 UrlCleaner，将 url 进行整合，减少统计粒度。
     *
     * @return url 清理
     */
    @Bean
    public UrlCleaner urlCleaner() {
        return originUrl -> {
            if (originUrl.contains("{")) {
                originUrl = originUrl.replaceAll("/\\{.+}", "");

            }
            return originUrl;
        };
    }

}
