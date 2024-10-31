package com.project.bridgetalkbackend.dto;

import com.project.bridgetalkbackend.domain.ChatRoom;
import com.project.bridgetalkbackend.domain.Message;

import java.util.List;

public class ChatListRecentResponse {
    List<ChatRoom> chatRooms;
    List<Message> messageList;

    public ChatListRecentResponse(List<ChatRoom> chatRooms, List<Message> messageList) {
        this.chatRooms = chatRooms;
        this.messageList = messageList;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(List<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
