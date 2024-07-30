package cn.rentaotao.order.config;

import com.alibaba.csp.sentinel.adapter.dubbo.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallback;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 注册 dubbo 流控逻辑
 *
 * @author rtt
 * @date 2024/7/29 11:08
 */
@Component
public class OrderApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        DubboAdapterGlobalConfig.setConsumerFallback(new DubboFallback() {

            @Override
            public Result handle(Invoker<?> invoker, Invocation invocation, BlockException e) {
                return invoker.invoke(invocation);
            }
        });
    }
}
