package cn.rentaotao.server.web;

import cn.rentaotao.server.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 测试分布式事务
 *
 * @author rtt
 * @date 2023/9/6 16:20
 */
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @RequestMapping("create")
    public String create(@RequestBody Map<String, Object> data) {
        purchaseService.create((Integer) data.get("userId"), (Integer) data.get("fee"));
        return "success";
    }
}
