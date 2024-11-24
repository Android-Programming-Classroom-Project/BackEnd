package com.project.bridgetalkbackend.Service;

import com.project.bridgetalkbackend.domain.ChatRoom;
import com.project.bridgetalkbackend.domain.User;
import com.project.bridgetalkbackend.repository.ChatRoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ChatRoomService {
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomService.class);
    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    //chat 생성
    @Transactional
    public ChatRoom makeChatRoom(UUID userId, UUID userId1){
        ChatRoom chatRoom = new ChatRoom();
        User user = new User();
        user.setUserId(userId);
        chatRoom.setUser(user);
        User user1 = new User();
        user.setUserId(userId);
        chatRoom.setUser1(user1);
        return chatRoomRepository.save(chatRoom);
    }
    //test용
    @Transactional
    public ChatRoom makeTestChatRoom(User user){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setUser(user);
        return chatRoomRepository.save(chatRoom);
    }


    //chat 목록 조회
    @Transactional(readOnly = true)
    public List<ChatRoom> chatRoomsFind(User user){
        logger.info("chatRoomService: chatRoomsFind Method");
        return chatRoomRepository.findByUserUserIdOrUser1UserId(user.getUserId(), user.getUserId());
    }

    //chat 삭제
    @Transactional
    public ChatRoom deleteChatroom(User user){
        return chatRoomRepository.deleteByUserUserId(user.getUserId());
    }
}
