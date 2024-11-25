package com.project.bridgetalkbackend.Service;

import com.project.bridgetalkbackend.domain.ChatRoom;
import com.project.bridgetalkbackend.domain.Comment;
import com.project.bridgetalkbackend.domain.Message;
import com.project.bridgetalkbackend.domain.User;
import com.project.bridgetalkbackend.dto.ChatItem;
import com.project.bridgetalkbackend.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // 가장 최근 메세지 정보가 져오기
    public List<ChatItem> getMessageRecently(User user,List<ChatRoom> chatList){
        List<ChatItem> chats  = new ArrayList<>();
        List<Message> messageList = new ArrayList<>();
        for(var chatRoom : chatList){
            if(messageRepository.existsByChatRoomRoomIdAndUserUserId(chatRoom.getRoomId(),user.getUserId())){
                Message message = messageRepository.findFirstByUserUserIdAndChatRoomRoomIdOrderByCreatedAtDesc(user.getUserId(),chatRoom.getRoomId());
                UUID roomId = chatRoom.getRoomId();
                String m = message.getContent();
                if(message.getContent().isEmpty()){
                    m = "";
                }
                chats.add(new ChatItem(roomId,m,user,user.getSchools(),chatRoom.getCreatedAt().toString()));
                messageList.add(message);
            }
        }
        return chats;
    }

    // 채팅 내역 가저오기
    @Transactional(readOnly = true)
    public List<Message> getMessageList(User user, ChatRoom chatRoom){
        List<Message> messageList = messageRepository.findByChatRoomRoomId(chatRoom.getRoomId());
        List<Message> messageUpdate = new ArrayList<>();
        for(var message : messageList){
            if(user.getUserId().equals(message.getUser().getUserId())){
                message.setSent(true);
            }
            else{
                message.setSent(false);
            }
            messageUpdate.add(message);
        }
        return messageUpdate;
    }

    //채팅 내역 저장
    @Transactional
    public void saveMessage(Message message){
        messageRepository.save(message);
    }
}
