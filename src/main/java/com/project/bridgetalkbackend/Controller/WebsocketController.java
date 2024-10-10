package com.project.bridgetalkbackend.Controller;

import com.project.bridgetalkbackend.dto.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.util.HtmlUtils;

public class WebsocketController {
@MessageMapping("/hello")
@SendTo("/pub/greetings")
public MessageDTO greeting(MessageDTO message) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new MessageDTO(message.getContent());
}
}

