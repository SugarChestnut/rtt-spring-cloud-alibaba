package cn.rentaotao.server.web;

import cn.rentaotao.server.service.IndexService;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rtt
 * @date 2023/8/18 10:45
 */
@RestController
@RequestMapping("index")
public class IndexController {

    private final RestTemplate restTemplate;

    private final IndexService indexService;

    @Autowired
    public IndexController(RestTemplate restTemplate, IndexService indexService) {
        this.restTemplate = restTemplate;
        this.indexService = indexService;
    }

    /**
     * 如果要在 controller 中使用热点参数规则，必须使用 @SentinelResource
     * 因为 url 资源，本质是使用拦截器，在找到指定 controller 之前，已经开始判断，这时候，没有获取到指定方法签名，
     * 从而使用了默认的 [] 无参列表，导致热点参数不匹配，从而使规则不生效
     */
    @RequestMapping("/i/{id}")
    @SentinelResource(value = "indexController", entryType = EntryType.IN, defaultFallback = "defaultFallback")
    public Map<String, Object> index(@PathVariable("id") Integer id) {
        Map<String, Object> result = indexService.index(id);
        result.put("userId", id);
        result.put("username", restTemplate.getForObject("http://rtt-user/user/find/" + id, String.class));

        return result;
    }

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
