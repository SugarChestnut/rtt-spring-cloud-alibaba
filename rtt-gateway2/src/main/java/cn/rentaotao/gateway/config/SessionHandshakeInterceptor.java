package cn.rentaotao.gateway.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;

/**
 * 握手配置，在 springboot 中应该不需要这个配置
 *
 * @author rtt
 * @date 2024/11/21 08:53
 */
public class SessionHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        /*
            attributes：从 request 获取用户信息，设置到 map 中，后期可以从 WebSocketHandler 的
            WebSocketSession 中拿出来
         */
        System.out.println("Handshake");
        HttpSession session = null;
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            Enumeration<String> headerNames = servletRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                System.out.println(headerNames.nextElement());
            }
            session = ((ServletServerHttpRequest) request).getServletRequest().getSession(false);
        }
        if (session != null) {
            // 解析 session 参数到 attributes
        }
        // 如果握手失败，通过 response 返回
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception) {
        System.out.println("After handshake");
    }
}
