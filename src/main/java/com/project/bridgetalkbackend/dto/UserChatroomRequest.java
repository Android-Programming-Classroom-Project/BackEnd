package com.project.bridgetalkbackend.dto;

import com.project.bridgetalkbackend.domain.ChatRoom;
import com.project.bridgetalkbackend.domain.User;

public class UserChatroomRequest {
    private User user;
    private ChatRoom chatRoom;

    public UserChatroomRequest(User user, ChatRoom chatRoom) {
        this.user = user;
        this.chatRoom = chatRoom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
