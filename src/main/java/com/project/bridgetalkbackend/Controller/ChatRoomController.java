package com.project.bridgetalkbackend.Controller;

import com.project.bridgetalkbackend.Service.ChatRoomService;
import com.project.bridgetalkbackend.Service.MessageService;
import com.project.bridgetalkbackend.domain.ChatRoom;
import com.project.bridgetalkbackend.domain.Message;
import com.project.bridgetalkbackend.domain.User;
import com.project.bridgetalkbackend.dto.ChatListRecentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomController.class);
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService, MessageService messageService) {
        this.chatRoomService = chatRoomService;
        this.messageService = messageService;
    }

    //채팅 목록 조회
    @GetMapping
    public ResponseEntity<?> getChatList(@RequestBody User user){
        logger.info("채팅목록 조회: /chat");
        List<ChatRoom> chatRoomList =  chatRoomService.chatRoomsFind(user);
        List<Message> messageList = messageService.getMessageRecently(user,chatRoomList);
        return ResponseEntity.ok(new ChatListRecentResponse(chatRoomList,messageList));
    }

}
