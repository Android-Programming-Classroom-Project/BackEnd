package com.project.bridgetalkbackend.Controller;

import com.project.bridgetalkbackend.Service.ChatRoomService;
import com.project.bridgetalkbackend.Service.MessageService;
import com.project.bridgetalkbackend.Service.PostService;
import com.project.bridgetalkbackend.domain.*;
import com.project.bridgetalkbackend.dto.ChatItem;
import com.project.bridgetalkbackend.dto.ChatListRecentResponse;
import com.project.bridgetalkbackend.dto.PostUserDTO;
import com.project.bridgetalkbackend.dto.UserChatroomRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomController.class);
    private final ChatRoomService chatRoomService;
    private final PostService postService;
    private final MessageService messageService;

    public ChatRoomController(ChatRoomService chatRoomService, PostService postService, MessageService messageService) {
        this.chatRoomService = chatRoomService;
        this.postService = postService;
        this.messageService = messageService;
    }

    //채팅 목록 조회
    @PostMapping("/")
    public ResponseEntity<?> getChatList(@RequestBody User user){
        List<ChatItem> chatList;
        logger.info("채팅목록 조회: /chat");
        List<ChatRoom> chatRoomList =  chatRoomService.chatRoomsFind(user);
        for(var room : chatRoomList){
            room.setUser(user);
        }
        chatList = messageService.getMessageRecently(user,chatRoomList);
        return ResponseEntity.ok(chatList);
    }

    // 채팅 내역 가저오기
    @PostMapping("/message")
    public ResponseEntity<?> getMessage(@RequestBody UserChatroomRequest userChatroomRequest){
        if(userChatroomRequest.getUser().getUserId() != null && userChatroomRequest.getChatRoom().getRoomId() != null){
            List<Message> messageList = messageService.getMessageList(userChatroomRequest.getUser(), userChatroomRequest.getChatRoom());
            return ResponseEntity.ok(messageList);
        }
        return (ResponseEntity<?>) ResponseEntity.badRequest();
    }

    @PostMapping("/makeChatroom")
    public ResponseEntity<?> makeChatroom(@RequestBody PostUserDTO postUserDTO){
        if(postUserDTO.getPost().getPostId() != null ){
            Post p = postService.postFind(postUserDTO.getPost().getPostId());
            if(postUserDTO.getUser().getUserId() == p.getUser().getUserId()){
                throw new IllegalArgumentException("사용자가 만든 게시물입니다.");
            }
            return ResponseEntity.ok( chatRoomService.makeChatRoom(p.getUser().getUserId(), postUserDTO.getUser().getUserId()));
        }
        return (ResponseEntity<?>) ResponseEntity.badRequest();
    }
//    // 랜덤 매칭
    @PostMapping("/randomMatching")
    public Map<String, Object> match(@RequestParam UUID userId) {
    Map<String, Object> response = new HashMap<>();
    try {
        Optional<UUID> matchedUser = chatRoomService.findMatch(userId);

        if (matchedUser.isPresent()) {
            // 매칭된 사용자와 새로운 채팅방 생성
            ChatRoom chatRoom = chatRoomService.makeChatRoom(userId, matchedUser.get());
            response.put("status", "matched");
            response.put("chatRoomId", chatRoom);
        } else {
            response.put("status", "waiting");
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        response.put("status", "error");
        response.put("message", "매칭 중 오류가 발생했습니다.");
    }

    return response;
}

    @PostMapping("/makeChatroomTest")
    public ResponseEntity<?> makeChatroom1(@RequestBody User user){
        if(user.getUserId() != null){
            return ResponseEntity.ok(chatRoomService.makeTestChatRoom(user));
        }
        return (ResponseEntity<?>) ResponseEntity.badRequest();
    }
}
