package cn.rentaotao.gateway.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.List;

/**
 * @author rtt
 * @date 2024/11/21 09:47
 */
public class WebSocketInboundInterceptor implements ChannelInterceptor {

    /**
     * 在消息发送前调用
     * @param message
     * @param channel
     * @return 如果返回为 null，不会发生实际的消息发送
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) {
            return null;
        }
        StompCommand command = accessor.getCommand();
        if (StompCommand.CONNECT.equals(command)) {
            // 连接
            List<String> usernames = accessor.getNativeHeader("username");
            if (usernames == null) {
                return null;
            }
            String username = usernames.get(0);
        }
        if (StompCommand.DISCONNECT.equals(command)) {
            // 断开连接
        }
//                Principal user = accessor.getUser();

        return message;
    }

    /**
     * 在消息发送后调用
     * @param message
     * @param channel
     * @param sent 目前没啥意义，无异常返回都是 true
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
    }
}
