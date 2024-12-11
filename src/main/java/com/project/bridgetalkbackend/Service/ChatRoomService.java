package com.project.bridgetalkbackend.Service;

import com.project.bridgetalkbackend.domain.ChatRoom;
import com.project.bridgetalkbackend.domain.User;
import com.project.bridgetalkbackend.repository.ChatRoomRepository;
import com.project.bridgetalkbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class ChatRoomService {
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomService.class);
    private final ChatRoomRepository chatRoomRepository;
    private final BlockingQueue<UUID> waitingQueue = new LinkedBlockingQueue<>();
    private final UserRepository userRepository;
    private ChatRoom room = null;

    public ChatRoomService(ChatRoomRepository chatRoomRepository, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
    }

    public ChatRoom randomMatching(UUID userId) {
        if (room == null) {
            User u = new User();
            u.setUserId(userId);
            this.room = makeTestChatRoom(u);
        } else {
            ChatRoom a = room;
            room = null;
            return a;
        }
        return room;
    }

    //chat 생성
    @Transactional
    public ChatRoom makeChatRoom(UUID userId, UUID userId1) {
        ChatRoom chatRoom = new ChatRoom();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        User user1 = userRepository.findById(userId1).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId1));
        user.setUserId(userId);
        user1.setUserId(userId1);
        chatRoom.setUser(user);
        chatRoom.setUser1(user1);
        return chatRoomRepository.save(chatRoom);
    }

    //test용
    @Transactional
    public ChatRoom makeTestChatRoom(User user) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setUser(user);
        return chatRoomRepository.save(chatRoom);
    }


    //chat 목록 조회
    @Transactional(readOnly = true)
    public List<ChatRoom> chatRoomsFind(User user) {
        logger.info("chatRoomService: 채팅목록 찾기 메소드 실행");
        return chatRoomRepository.findByUser_UserIdOrUser1_UserId(user.getUserId(), user.getUserId());
    }

    //chat  삭제
    @Transactional
    public void deleteChatRoom(UUID roomId){
        if(chatRoomRepository.existsByRoomId(roomId)){
            chatRoomRepository.deleteByRoomId(roomId);
        }
        else{
            throw new IllegalArgumentException("서버 에러");
        }
    }


    public Optional<UUID> findMatch(UUID userId) throws InterruptedException {
        // 대기열에 사용자를 추가
        boolean added = waitingQueue.offer(userId, 1, TimeUnit.SECONDS);
        if (!added) {
            return Optional.empty();
        }

        // 1분 동안 대기하며 다른 사용자를 기다림
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 60000) { // 1분 대기
            UUID otherUserId = waitingQueue.poll(1, TimeUnit.SECONDS);
            if (otherUserId != null && !otherUserId.equals(userId)) {
                // 매칭 성공 시
                return Optional.of(otherUserId);
            } else if (otherUserId != null) {
                // 자신이 대기열에서 나왔다면 다시 대기열에 추가
                waitingQueue.offer(otherUserId, 1, TimeUnit.SECONDS);
            }
        }
        // 1분 동안 매칭되지 않으면 대기열에서 자신을 제거
        waitingQueue.remove(userId);
        return Optional.empty();
    }
}
