package com.project.bridgetalkbackend.dto;

import com.project.bridgetalkbackend.domain.Schools;
import com.project.bridgetalkbackend.domain.User;
import java.util.UUID;
public class ChatItem {
    private UUID roomId;
    private String lastMessage;
    private User user;
    private Schools school;
    private String createdAt;

    public ChatItem(UUID roomId, String lastMessage, User user, Schools school, String createdAt) {
        this.roomId = roomId;
        this.lastMessage = lastMessage;
        this.user = user;
        this.school = school;
        this.createdAt = createdAt;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Schools getSchool() {
        return school;
    }

    public void setSchool(Schools school) {
        this.school = school;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
