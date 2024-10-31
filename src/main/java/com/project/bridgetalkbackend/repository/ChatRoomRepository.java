package com.project.bridgetalkbackend.repository;

import com.project.bridgetalkbackend.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
    List<ChatRoom> findByUserUserId(UUID userId);

    ChatRoom deleteByUserUserId(UUID userId);
}
