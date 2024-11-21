package cn.rentaotao.gateway.controller;

import cn.rentaotao.gateway.domain.dto.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * @author rtt
 * @date 2024/11/11 16:01
 */
@Controller
@AllArgsConstructor
public class WebSocketStompController {

    private final SimpMessagingTemplate template;

    /**
     * 单纯接收客户端消息（POST），如果有返回内容，会转到 /topic/receive
     */
    @MessageMapping("/receive")
    public void receive(String msg) {
        System.out.println(msg);
    }

    /**
     * 客户单获取资源（GET），或者就是请求一下
     * 使用 @SubscribeMapping，不能有参数，会将返回内容直接返回给客户端
     * 如果加上 @SendTo 注解，可以转到订阅中 /topic/source
     */
    @SubscribeMapping("/source")
    public String source() {
        return "收到";
    }

    /**
     * 接收 send 命令的 消息，一般客户端可以直接将数据投递到 /topic/notice，使用这个的话，可以对消息做特殊处理
     * 如果没有 @SendTo，那么返回内容不是直接返回，而是投递到 /topic/sendToAll
     *
     * @param message 消息
     * @return 返回
     */
    @MessageMapping("/sendToAll")
    @SendTo("/topic/notice")
    public String sendToAll(String message) {
        System.out.println(message);
        return message;
    }

    /**
     * 如果有返回值，且带有 @SendToUser 表明是给发送方回复消息发送的状态，比如不在线，消息拒收等
     * 如果不关心，设置为 void
     *
     * @param userId  目标用户ID
     * @param message 消息结构体
     * @throws JsonProcessingException ex
     */
    @MessageMapping("/sendToUser/{userId}")
    public void sendToUser(@DestinationVariable("userId") String userId, String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);
        System.out.printf("发送者: %s, 接收者: %s%n", chatMessage.getSender(), userId);
        // 主要是因为 sendToUser 会对路径作特殊处理，不然跟 topic 广播消息没啥区别
        template.convertAndSendToUser(userId, "/message", chatMessage);
    }

    @MessageExceptionHandler(Exception.class)
    @SendToUser("/queue/")
    public Exception exception(Exception ex) {
        ex.printStackTrace();
        return ex;
    }


}
