package cn.rentaotao.gateway.base;

import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author rtt
 * @date 2024/11/21 15:16
 */
@CrossOrigin("*")
@ServerEndpoint(value = "/chat/{userId}", configurator = ChatEndpointConfig.class)
public class ChatEndpoint {

    // 可以有其他用户信息
    private static final ConcurrentHashMap<String, Session> users = new ConcurrentHashMap<>();

    /**
     * 当连接成功
     *
     * @param session 第一个参数必须是 session
     * @param ec
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig ec, @PathParam("userId") String userId) {
        Map<String, Object> userProperties = ec.getUserProperties();
        String connectedId = userProperties.get("connectedId").toString();
        users.put(connectedId, session);
    }

    /**
     * 连接断开
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        users.forEach((id, s) -> {
            try {
                // 异步发送
//                s.getAsyncRemote().sendText("xx");
                s.getBasicRemote().sendText("下线了");
            } catch (Exception e) {
                // 通知失败
            }
        });
    }

    @OnMessage
    public void onMessage(Session session, String message) {

    }

    @OnError
    public void onError(Throwable error) {

    }

}
