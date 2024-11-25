package cn.rentaotao.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * @author rtt
 * @date 2024/11/11 16:30
 */
@Configuration
public class WebSocketAutoConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 配置端点，客户端在订阅或发布消息到目的地路径前，要链接端点
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new SessionHandshakeInterceptor())
                .setHandshakeHandler(new PrincipalHandshakeHandler());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*
            请求以 /topic 为前缀，表示订阅消息，订阅相同路径的用户都能收到消息
         */
        registry.enableSimpleBroker( "/topic", "/user1");
        /*
            客户端向服务端发送消息，带此前缀的请求会被 controller 处理，AnnotationMethodMessageHandler
            send 命令 -> @MessageMapping 接口处理
            subscribe 命令 -> @SubscribeMapping 接口处理
         */
        registry.setApplicationDestinationPrefixes("/app");
        // 给指定客户端发消息，UserDestinationMessageHandler 处理
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // 设置处理线程池
//        registration.taskExecutor();
        registration.interceptors(new WebSocketInboundInterceptor());
    }

    /**
     * 配置发送与接收的消息参数，可以指定消息字节大小，缓存大小，发送超时时间
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        // 添加装饰器，例如在 spring security 中，如果 session 过期，强制关闭 websocket session
//        registry.addDecoratorFactory();
        registry.setMessageSizeLimit(64 * 1024)
                .setSendBufferSizeLimit(512 * 1024)
                .setSendTimeLimit(10000)
                .setTimeToFirstMessage(60 * 1000);
    }
}
