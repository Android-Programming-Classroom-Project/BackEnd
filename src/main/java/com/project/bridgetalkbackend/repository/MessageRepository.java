package com.project.bridgetalkbackend.repository;

import com.project.bridgetalkbackend.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    Message findFirstByUserUserIdAndChatRoomRoomIdOrderByCreatedAtDesc(UUID userId, UUID roomId);
    boolean existsByChatRoomRoomIdAndUserUserId(UUID roomId, UUID userId);

    boolean existsByUserUserId(UUID userId);
}
