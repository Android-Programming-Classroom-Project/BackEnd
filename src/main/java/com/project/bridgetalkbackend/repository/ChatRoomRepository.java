package com.project.bridgetalkbackend.repository;

import com.project.bridgetalkbackend.domain.ChatRoom;
import com.project.bridgetalkbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
    List<ChatRoom> findByUserUserId(UUID userId);
    List<ChatRoom> findByUser_UserIdOrUser1_UserId(UUID userId, UUID user1Id);
    boolean existsByUserUserIdOrUser1UserId(UUID userId, UUID user1Id);
    boolean existsByRoomId(UUID roomId);
    void deleteByRoomId(UUID roomId);
}
