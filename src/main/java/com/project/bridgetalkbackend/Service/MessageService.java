package com.project.bridgetalkbackend.Service;

import com.project.bridgetalkbackend.domain.ChatRoom;
import com.project.bridgetalkbackend.domain.Comment;
import com.project.bridgetalkbackend.domain.Message;
import com.project.bridgetalkbackend.domain.User;
import com.project.bridgetalkbackend.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // 가장 최근 메세지 정보가 져오기
    public List<Message> getMessageRecently(User user,List<ChatRoom> chatList){
        List<Message> messageList = new ArrayList<>();
        for(var chatRoom : chatList){
            if(messageRepository.existsByChatRoomRoomIdAndUserUserId(chatRoom.getRoomId(),user.getUserId())){
                Message message = messageRepository.findFirstByUserUserIdAndChatRoomRoomIdOrderByCreatedAtDesc(user.getUserId(),chatRoom.getRoomId());
                messageList.add(message);
            }
        }
        if(messageList.isEmpty()){
            return null;
        }
        return messageList;
    }
}
