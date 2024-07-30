package cn.rentaotao.product.config.listener;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author rtt
 * @date 2024/7/24 11:20
 */
@Component
public class ProductPropertiesNacosListener implements ApplicationRunner {

    @Value("${spring.cloud.nacos.config.name}")
    private String configDataId;

    @Value("${spring.cloud.nacos.config.group}")
    private String configGroup;

    private final NacosConfigManager configManager;

    public ProductPropertiesNacosListener(NacosConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("[ProductNacosPropertiesListener] add listener");

        configManager.getConfigService().addListener(configDataId, configGroup, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                String time = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
                System.out.println("[listener] " + time + " -- " + configInfo);
//                System.out.println("[before] " + productProperties);
//                Properties properties = new Properties();
//                try {
//                    properties.load(new StringReader(configInfo));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                productProperties.setName(properties.getProperty("product.name"));
//                productProperties.setNum(Integer.valueOf(properties.getProperty("product.num")));
            }
        });
    }
}
