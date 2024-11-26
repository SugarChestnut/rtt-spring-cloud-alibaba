package cn.rentaotao.gateway.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author rtt
 * @date 2024/11/21 09:47
 */
public class WebSocketInboundInterceptor implements ChannelInterceptor {

    /**
     * 在消息发送前调用
     *
     * @param message
     * @param channel
     * @return 如果返回为 null，不会发生实际的消息发送
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("Pre send");
        MessageHeaders headers = message.getHeaders();
        Principal user = SimpMessageHeaderAccessor.getUser(headers);
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        // 创建 accessor
        SimpMessageHeaderAccessor simpMessageHeaderAccessor = SimpMessageHeaderAccessor.wrap(message);
        // 获取 accessor
        MessageHeaderAccessor.getAccessor(headers, SimpMessageHeaderAccessor.class);

        if (accessor != null) {
            StompCommand command = accessor.getCommand();
            Optional.ofNullable(accessor.getCommand()).ifPresent(a -> System.out.println(a.name()));
            if (StompCommand.CONNECT.equals(command)) {
                // 连接
                List<String> usernames = accessor.getNativeHeader("username");
                if (usernames != null) {
                    System.out.println(usernames.get(0));
                }
                System.out.println("建立连接");
            }
            if (StompCommand.DISCONNECT.equals(command)) {
                // 断开连接
                System.out.println("断开连接");
            }
            if (StompCommand.MESSAGE.equals(command)) {
                System.out.println("发送消息");
            }
        }
        return message;
    }

    /**
     * 在消息发送后调用
     *
     * @param message
     * @param channel
     * @param sent    目前没啥意义，无异常返回都是 true
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        System.out.println("Post send");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        Optional.ofNullable(accessor.getCommand()).ifPresent(a -> System.out.println(a.name()));
        /*
            拿到消息头对象后，我们可以做一系列业务操作
            1. 通过getSessionAttributes()方法获取到websocketSession，
               就可以取到我们在WebSocketHandshakeInterceptor拦截器中存在session中的信息
            2. 我们也可以获取到当前连接的状态，做一些统计，例如统计在线人数，或者缓存在线人数对应的令牌，方便后续业务调用
         */
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        HttpSession httpSession = null;
        if (sessionAttributes != null) {
            httpSession = (HttpSession) sessionAttributes.get("HTTP_SESSION");
        }

        if (httpSession == null) {
            return;
        }

        // 这里只是单纯的打印，可以根据项目的实际情况做业务处理
        System.out.println("postSend 中获取httpSession key：" + httpSession.getId());

        // 忽略心跳消息等非STOMP消息
        if (accessor.getCommand() == null) {
            return;
        }

        // 根据连接状态做处理，这里也只是打印了下，可以根据实际场景，对上线，下线，首次成功连接做处理
        System.out.println(accessor.getCommand());
        switch (accessor.getCommand()) {
            // 首次连接
            case CONNECT:
                System.out.println("httpSession key：" + httpSession.getId() + " 首次连接");
                break;
            // 连接中
            case CONNECTED:
                break;
            // 下线
            case DISCONNECT:
                System.out.println("httpSession key：" + httpSession.getId() + " 下线");
                break;
            default:
                break;
        }
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        System.out.println("After send completion");
    }
}
