package cn.rentaotao.product;

import cn.rentaotao.product.web.ProductController;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration;
import org.springframework.cloud.bootstrap.BootstrapConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.properties.ConfigurationPropertiesBeans;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.Set;

@SpringBootApplication
// 启用 dubbo
@EnableDubbo
//@DubboComponentScan
// 扫描 @ConfigurationProperties 类，而不需要再添加 @Configuration
@ConfigurationPropertiesScan
// 启用缓存
//@EnableCaching
@EnableDiscoveryClient
public class RttProductApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(RttProductApplication.class, args);
        ConfigurationPropertiesBeans configurationPropertiesBeans = applicationContext.getBean(ConfigurationPropertiesBeans.class);
        Set<String> beanNames = configurationPropertiesBeans.getBeanNames();
        System.out.println(beanNames.size());
        String[] beanNamesForType = applicationContext.getBeanNamesForType(ProductController.class);
    }

}
