package com.project.bridgetalkbackend.Handler;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {

    // 추후 jwt 토큰 검증해야함
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel){
        StompHeaderAccessor stompHandler = StompHeaderAccessor.wrap(message);
        StompCommand command = stompHandler.getCommand();

        if (command != null) {
            switch (command) {
                case CONNECT:
                    System.out.println("유저 접속...");
                    break;
                case DISCONNECT:
                    System.out.println("유저 퇴장...");
                    break;
                case SUBSCRIBE:
                    System.out.println("유저 구독...");
                    break;
                case UNSUBSCRIBE:
                    System.out.println("유저 구독 취소...");
                    return null;
                default:
                    System.out.println("다른 커맨드... : " + command);
                    break;
            }
        }
        return message;
    }

}
