package cn.rentaotao.gateway.base;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Map;

/**
 * @author rtt
 * @date 2024/11/21 15:18
 */
public class ChatEndpointConfig extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // 从握手中获取 session
        HttpSession httpSession = (HttpSession) request.getHttpSession();

        // 请求头
        Map<String, Object> userProperties = sec.getUserProperties();
    }
}
