package cn.rentaotao.gateway.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * 用户选择，如果使用了 spring security 应该是不需要实现这个的
 *
 * @author rtt
 * @date 2024/11/21 09:56
 */
public class PrincipalHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        ServletServerHttpRequest r = (ServletServerHttpRequest) request;
        HttpServletRequest servletRequest = r.getServletRequest();
        Cookie[] cookies = servletRequest.getCookies();
        // 确定用户信息
        for (Cookie cookie : cookies) {
            if("username".equals(cookie.getName())) {
                final String username = cookie.getValue();
                System.out.println("Determine user: " + username);
                return () -> username;
            }
        }

        return super.determineUser(request, wsHandler, attributes);
    }
}
