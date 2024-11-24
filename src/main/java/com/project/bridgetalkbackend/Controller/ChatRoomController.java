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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        List<ChatItem> chatList  = new ArrayList<>();
        logger.info("채팅목록 조회: /chat");
        List<ChatRoom> chatRoomList =  chatRoomService.chatRoomsFind(user);
        for(var room : chatRoomList){
            room.setUser(user);
        }
        List<Message> messageList = messageService.getMessageRecently(user,chatRoomList);
        for(int i = 0; i<chatRoomList.size(); i++){
            UUID roomId = chatRoomList.get(i).getRoomId();
            String content =  (messageList.get(i) != null) ? messageList.get(i).getContent() : "";
            User u = chatRoomList.get(i).getUser();
            Schools school = chatRoomList.get(i).getUser().getSchools();
            String createdAt = chatRoomList.get(i).getCreatedAt().toString();
            ChatItem chatItem = new ChatItem(roomId,content,u,school,createdAt);
            chatList.add(chatItem);
        }

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
//    @PostMapping("/randomMatching")
//    public ResponseEntity<?> randomMachingSystem(@RequestBody User user){
//
//        return ResponseEntity.status(204).body("매칭할 사용자가 없습니다");
//    }

    @PostMapping("/makeChatroomTest")
    public ResponseEntity<?> makeChatroom1(@RequestBody User user){
        if(user.getUserId() != null){
            return ResponseEntity.ok(chatRoomService.makeTestChatRoom(user));
        }
        return (ResponseEntity<?>) ResponseEntity.badRequest();
    }
}
