package com.project.bridgetalkbackend.dto;

import com.project.bridgetalkbackend.domain.ChatRoom;

import java.util.HashSet;
import java.util.UUID;

public class Matching {
    String type;
    UUID userId;
    HashSet<UUID> users;
    UUID chatRoom;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public HashSet<UUID> getUsers() {
        return users;
    }

    public void setUsers(HashSet<UUID> users) {
        this.users = users;
    }

    public UUID getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(UUID chatRoom) {
        this.chatRoom = chatRoom;
    }
}
