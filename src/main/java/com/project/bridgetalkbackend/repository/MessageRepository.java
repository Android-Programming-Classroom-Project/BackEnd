package com.project.bridgetalkbackend.repository;

import com.project.bridgetalkbackend.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    Message findFirstByChatRoomRoomIdOrderByCreatedAtDesc(UUID roomId);
    boolean existsByChatRoomRoomId(UUID roomId);
    List<Message> findByUserUserIdAndChatRoomRoomId(UUID userId, UUID roomId);

    List<Message> findByChatRoomRoomIdOrderByCreatedAtAsc(UUID roomId);
}
