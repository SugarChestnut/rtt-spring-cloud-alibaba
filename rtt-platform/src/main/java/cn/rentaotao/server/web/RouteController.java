package cn.rentaotao.server.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rtt
 * @date 2023/8/28 13:38
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    @RequestMapping("/v1")
    public String v1() {
        return "v1";
    }

    @RequestMapping("/v2")
    public String v2() {
        return "v2";
    }
}
