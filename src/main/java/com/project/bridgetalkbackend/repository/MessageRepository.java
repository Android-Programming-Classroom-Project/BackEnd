package com.project.bridgetalkbackend.repository;

import com.project.bridgetalkbackend.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}
