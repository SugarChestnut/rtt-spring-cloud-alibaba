package cn.rentaotao.order.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * spring cloud 配置自动更新
 *
 * @author rtt
 * @date 2024/6/20 13:50
 */
@Component
@RefreshScope
@Data
public class OrderCenterConfig {

    /**
     * 从配置中心获取 data-id 为 ${prefix}-${spring.profile.active}.${file-extension} 中获取配置项
     * 这里 prefix 为 application.name，拼接为 rtt-order.yml
     * 不同的配置中心，配置获取方式不同
     */
    @Value("${canBud:false}")
    public boolean canBuy;

}
