package cn.rentaotao.product.config.listener;

import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 监听 nacos 上的配置
 *
 * @author rtt
 * @date 2024/6/21 13:30
 */
public class ProductCloudConfig {


    public String productName;

    public Integer productNum;
}
