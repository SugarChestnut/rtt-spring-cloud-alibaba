package cn.rentaotao.gateway.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rtt
 * @date 2024/11/11 16:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private String sender;
    private String message;
    private Long timestamp;

    public ChatMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}
