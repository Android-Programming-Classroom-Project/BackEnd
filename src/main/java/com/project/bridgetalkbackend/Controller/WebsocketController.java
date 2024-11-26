package com.project.bridgetalkbackend.Controller;

import com.project.bridgetalkbackend.Service.ChatRoomService;
import com.project.bridgetalkbackend.Service.MessageService;
import com.project.bridgetalkbackend.domain.ChatRoom;
import com.project.bridgetalkbackend.domain.Message;
import com.project.bridgetalkbackend.dto.Matching;
import com.project.bridgetalkbackend.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Controller
public class WebsocketController {
    private final MessageService messageService;
    private final ChatRoomService chatRoomService;
    private static final Logger logger = LoggerFactory.getLogger(WebsocketController.class);

    public WebsocketController(MessageService messageService, ChatRoomService chatRoomService) {
        this.messageService = messageService;
        this.chatRoomService = chatRoomService;
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

    // Matching
    @MessageMapping("/matching")
        @SendTo("/sub/matching")
    public Matching meeting(Matching matching) throws Exception{
        logger.info("/matching");
        HashSet<UUID> users = new HashSet<>();
        users.add(matching.getUserId());
        if(users.size() == 2){
            matching.setType("matching");
            logger.info("matching 시작");
            matching.setUsers(users);
            List userList = new ArrayList<>(users);
            if(userList.size() == 2){
                users.clear();
                ChatRoom chatRoom=chatRoomService.makeChatRoom(UUID.fromString((String) userList.get(0)), UUID.fromString((String) userList.get(0)));
                matching.setChatRoom(chatRoom.getRoomId());
                return matching;
            }
            users.clear();
        }

        if(matching.getType() == "cancel"){
            users.remove(matching.getUserId());
        }

        return matching;
    }
}

