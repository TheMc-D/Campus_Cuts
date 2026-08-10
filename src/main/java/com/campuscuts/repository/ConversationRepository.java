package com.campuscuts.repository;

import com.campuscuts.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Optional<Conversation> findByStudentIdAndProviderId(Long studentId, Long providerId);

    List<Conversation> findByStudentId(Long studentId);

    List<Conversation> findByProviderId(Long providerId);
}
