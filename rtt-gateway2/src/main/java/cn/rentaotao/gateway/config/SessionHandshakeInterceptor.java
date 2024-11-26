package cn.rentaotao.gateway.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;

/**
 * 握手配置，在 springboot 中应该不需要这个配置
 *
 * @author rtt
 * @date 2024/11/21 08:53
 */
public class SessionHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("Handshake");
        HttpSession session = null;
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            Enumeration<String> headerNames = servletRequest.getHeaderNames();
            session = ((ServletServerHttpRequest) request).getServletRequest().getSession(false);
        }
        String id = UUID.randomUUID().toString();
        if (session == null) {
            // 一般情况下，没有权限校验，不会创建 session，创建临时 session
            Cookie jsessionid = new Cookie("JSESSIONID", "ws:" + id);
            HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
            servletResponse.addCookie(jsessionid);
        }
        // 如果握手失败，通过 response 返回
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception) {
        System.out.println("After handshake");
    }
}
