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

    @RequestMapping("/r1")
    public String r1() {
        return "r1";
    }

    @RequestMapping("/r2")
    public String r2() {
        return "r2";
    }
}
