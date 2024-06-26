package cn.rentaotao.customer.web;

import cn.rentaotao.customer.api.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author rtt
 * @date 2023/8/18 10:41
 */
@RestController
@RequestMapping("/user")
// 添加注解后，当 nacos 中的配置更新后，会刷新 @Value 中的值
@RefreshScope
public class CustomerController {

    private final CustomerService customerService;

    @Value("${user.name:yyl}")
    private String name;

    @Value("${user.age:18}")
    private String age;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping("/find/{id}")
    public String findUsername(@PathVariable("id") Integer id) throws InterruptedException {
        return name + (id == null ? age : id);
    }

    @RequestMapping("/deduct")
    public String deduct(@RequestBody Map<String, Object> data) {
        customerService.deduct((Integer) data.get("userId"), (Integer) data.get("fee"));
        return "success";
    }
}
