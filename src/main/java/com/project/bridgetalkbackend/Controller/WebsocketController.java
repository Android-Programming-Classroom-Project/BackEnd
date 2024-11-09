package com.project.bridgetalkbackend.Controller;

import com.project.bridgetalkbackend.Service.MessageService;
import com.project.bridgetalkbackend.domain.Message;
import com.project.bridgetalkbackend.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
    private final MessageService messageService;
    private static final Logger logger = LoggerFactory.getLogger(WebsocketController.class);

    public WebsocketController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/{roomId}")
    @SendTo("/sub/{roomId}")
    public Message greeting(@DestinationVariable String roomId, Message message) throws Exception {
        if(message != null){
            logger.info("message: "+ message.getContent());
            // 메세지 db 저장
            messageService.saveMessage(message);
        }
        return message;
    }
}

