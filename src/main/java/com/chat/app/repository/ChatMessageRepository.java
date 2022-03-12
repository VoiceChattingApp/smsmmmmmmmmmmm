package com.chat.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chat.app.entity.ChatMessage;
import com.chat.app.entity.MessageStatus;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
	long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, MessageStatus status);

	List<ChatMessage> findByChatId(String chatId);

}
