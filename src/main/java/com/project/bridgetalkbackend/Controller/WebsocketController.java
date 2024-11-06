package com.project.bridgetalkbackend.Controller;

import com.project.bridgetalkbackend.Service.MessageService;
import com.project.bridgetalkbackend.domain.Message;
import com.project.bridgetalkbackend.repository.MessageRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
    private final MessageService messageService;

    public WebsocketController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/{roomId}")
    @SendTo("/pub/{roomId}")
    public Message greeting(@DestinationVariable String roomId, Message message) throws Exception {
        if(message != null){
            System.out.println("message: "+ message.getContent());
            // 메세지 db 저장
            messageService.saveMessage(message);
        }
        return message;
    }
}

