package com.campuscuts.service;

import com.campuscuts.dto.MessageForm;
import com.campuscuts.entity.Conversation;
import com.campuscuts.entity.Message;
import com.campuscuts.entity.Provider;
import com.campuscuts.entity.User;
import com.campuscuts.repository.ConversationRepository;
import com.campuscuts.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessagingService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public MessagingService(ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    public List<Conversation> inboxFor(User user) {
        if (user.getProvider() != null) {
            List<Conversation> asProvider = conversationRepository.findByProviderId(user.getProvider().getId());
            List<Conversation> asStudent = conversationRepository.findByStudentId(user.getId());
            return java.util.stream.Stream.concat(asProvider.stream(), asStudent.stream()).toList();
        }
        return conversationRepository.findByStudentId(user.getId());
    }

    public Conversation getConversation(Long id) {
        return conversationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found: " + id));
    }

    @Transactional
    public Conversation startOrResume(User student, Provider provider) {
        return conversationRepository.findByStudentIdAndProviderId(student.getId(), provider.getId())
                .orElseGet(() -> {
                    Conversation conversation = new Conversation();
                    conversation.setStudent(student);
                    conversation.setProvider(provider);
                    return conversationRepository.save(conversation);
                });
    }

    @Transactional
    public Message postMessage(Conversation conversation, User sender, MessageForm form) {
        assertParticipant(conversation, sender);
        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setBody(form.getBody());
        return messageRepository.save(message);
    }

    public boolean isParticipant(Conversation conversation, User user) {
        boolean isStudent = conversation.getStudent().getId().equals(user.getId());
        boolean isProviderOwner = user.getProvider() != null
                && conversation.getProvider().getId().equals(user.getProvider().getId());
        return isStudent || isProviderOwner;
    }

    private void assertParticipant(Conversation conversation, User user) {
        if (!isParticipant(conversation, user)) {
            throw new IllegalStateException("You are not a participant in this conversation");
        }
    }
}
