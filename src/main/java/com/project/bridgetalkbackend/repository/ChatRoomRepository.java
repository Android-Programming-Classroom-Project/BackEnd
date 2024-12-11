package com.project.bridgetalkbackend.repository;

import com.project.bridgetalkbackend.domain.ChatRoom;
import com.project.bridgetalkbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
    ChatRoom findByUserUserIdAndUser1UserId(UUID userId, UUID userId1);
    List<ChatRoom> findByUser_UserIdOrUser1_UserId(UUID userId, UUID user1Id);
    @Transactional(readOnly = true)
    boolean existsByUserUserIdAndUser1UserId(UUID userId, UUID user1Id);
    boolean existsByRoomId(UUID roomId);
    void deleteByRoomId(UUID roomId);
}
