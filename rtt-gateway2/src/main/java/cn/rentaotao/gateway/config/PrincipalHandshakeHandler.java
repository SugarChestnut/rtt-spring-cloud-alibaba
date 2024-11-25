package cn.rentaotao.gateway.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * 用户认证处理
 *
 * @author rtt
 * @date 2024/11/21 09:56
 */
public class PrincipalHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // 确定用户信息
        System.out.println("Determine user");
        return super.determineUser(request, wsHandler, attributes);
    }
}
