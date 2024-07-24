package cn.rentaotao.product.config.listener;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
                Executor executor = getExecutor();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("[ProductNacosConfigListener]" + configInfo);
                    }
                };
                if (executor != null) {
                    executor.execute(runnable);
                } else {
                    runnable.run();
                }
            }
        });
    }
}
