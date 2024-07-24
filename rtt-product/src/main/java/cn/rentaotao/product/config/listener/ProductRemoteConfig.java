package cn.rentaotao.product.config.listener;

import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import javax.annotation.PostConstruct;

/**
 * 监听 nacos 上的配置
 *
 * @author rtt
 * @date 2024/6/21 13:30
 */
@Data
@RefreshScope
@ConfigurationProperties("product")
public class ProductRemoteConfig implements InitializingBean, DisposableBean {

    public String name;

    public Integer num;

    @Override
    public void destroy() throws Exception {
        // 在配置刷新的时候，先销毁
        System.out.printf("[destroy] name: %s, num: %d%n", name, num);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 在配置刷新的时候，重新初始化
        System.out.printf("[afterPropertiesSet] name: %s, num: %d%n", name, num);
    }

    @PostConstruct
    public void init() {
        System.out.printf("[init] name: %s, num: %d%n", name, num);
    }
}
