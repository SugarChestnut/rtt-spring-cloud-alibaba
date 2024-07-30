package cn.rentaotao.product.config.listener;

import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 监听 nacos 上的配置
 *
 * @author rtt
 * @date 2024/6/21 13:30
 */
@RefreshScope
@ConfigurationProperties("product")
public class ProductProperties implements InitializingBean, DisposableBean {

    private Boolean canSale = false;

    private List<String> excludes = new ArrayList<>();

    @Override
    public void destroy() throws Exception {
        // 1、在配置刷新的时候，先销毁
        System.out.printf("[destroy] canSale: %s, excludes: %s%n", canSale, excludes.toString());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 3、在配置刷新的时候，重新初始化
        System.out.printf("[afterPropertiesSet] canSale: %s, excludes: %s%n", canSale, excludes.toString());
    }

    @PostConstruct
    public void init() {
        // 2、
        System.out.printf("[init] canSale: %s, excludes: %s%n", canSale, excludes.toString());
    }

    public Boolean getCanSale() {
        return canSale;
    }

    public void setCanSale(Boolean canSale) {
        this.canSale = canSale;
    }

    public List<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }
}
