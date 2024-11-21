package cn.rentaotao.gateway.controller;

import cn.rentaotao.gateway.domain.dto.ChatMessage;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rtt
 * @date 2024/11/19 10:05
 */
@RestController
@RequestMapping("/index")
@AllArgsConstructor
public class IndexController {

    private final SimpMessagingTemplate template;

    @RequestMapping("/send")
    public String send() {
        template.convertAndSend("/topic/notice", new ChatMessage("System", "系统广播"));
        return "ok";
    }

    @RequestMapping("/send/{username}")
    public String sendToUser(@PathVariable("username") String username) {
        System.out.println(username);
        ChatMessage chatMessage = new ChatMessage("Server", "系统通知");
        // /user/{user}/message
        template.convertAndSendToUser(username, "/message",chatMessage);
        return "ok";
    }

}
