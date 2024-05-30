package cn.rentaotao.server.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 限流测试
 *
 * @author rtt
 * @date 2023/8/21 15:43
 */
@Service
public class IndexServiceImpl implements IndexService {

    /**
     * SentinelResource 注解: 标识一个资源
     * value: 资源名称
     * entryType: entry 类型
     * blockHandler: 对应处理 BlockException 的函数名称，函数必须是 public，返回类型与原方法一致，方法参数前面与原方法一致，在后面增加一个 BlockException 类型的参数
     * 该方法默认与原方法在同一个类中
     * blockHandlerClass: 如果 blockHandler 在其他类中，则通过这个参数指定，但是方法必须是 static
     * fallback: 抛出异常的处理逻辑
     * fallbackClass:
     * defaultFallback:
     * exceptionsToIgnore:
     *
     * @param id id
     * @return result
     */
    @Override
    @SentinelResource(value = "IndexService#index", defaultFallback = "defaultFallback")
    public Map<String, Object> index(Integer id) {
        Map<String, Object> result = new HashMap<>();
        result.put("userId", id);
        return result;
    }

    /**
     * blockHandler 对应处理 BlockException 的函数名称
     *
     * @param id
     * @param e
     * @return
     */
    public Map<String, Object> blockHandler(Integer id, BlockException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("err", "BlockHandler: Blocked by Sentinel");
        return result;
    }

    /**
     * fallback 函数名称，可选项，用于在抛出异常的时候提供 fallback 处理逻辑。
     * fallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理
     *
     * @param id
     * @param t
     * @return
     */
    public Map<String, Object> fallback(Integer id, Throwable t) {
        Map<String, Object> result = new HashMap<>();
        result.put("err", "Fallback: Blocked by Sentinel - " + t.getClass().getCanonicalName());
        return result;
    }

    /**
     * defaultFallback（since 1.6.0）：默认的 fallback 函数名称，可选项，通常用于通用的 fallback 逻辑（即可以用于很多服务或方法）
     *
     * @param t
     * @return
     */
    public Map<String, Object> defaultFallback(Throwable t) {
        Map<String, Object> result = new HashMap<>();
        if (BlockException.isBlockException(t)) {
            result.put("err", "DefaultFallback: Blocked by Sentinel - " + t.getClass().getSimpleName());
        } else {
            result.put("err", "DefaultFallback: Oops, failed - " + t.getClass().getCanonicalName());
        }
        return result;
    }
}
