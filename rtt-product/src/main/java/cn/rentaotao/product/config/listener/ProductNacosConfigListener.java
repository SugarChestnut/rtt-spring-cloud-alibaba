package cn.rentaotao.product.config.listener;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author rtt
 * @date 2024/7/24 11:20
 */
@Component
public class ProductNacosConfigListener implements ApplicationRunner {

    private final NacosConfigManager configManager;

    public ProductNacosConfigListener(NacosConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String group = "service";
        String dataId = "product-service-base";
        configManager.getConfigService().addListener(dataId, group, new Listener() {

            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println(configInfo);
                Properties properties = new Properties();
                try {
                    properties.load(new StringReader(configInfo));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Executor executor = getExecutor();
                Runnable runnable = () -> System.out.printf("[ProductNacosConfigListener] name: %s, num: %d%n",
                        properties.getProperty("name"),
                        (int) properties.get("num"));
                if (executor != null) {
                    executor.execute(runnable);
                } else {
                    runnable.run();
                }
            }
        });
    }
}
