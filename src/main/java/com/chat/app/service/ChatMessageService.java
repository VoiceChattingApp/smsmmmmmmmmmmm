package com.chat.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.chat.app.entity.ChatMessage;
import com.chat.app.entity.MessageStatus;
import com.chat.app.repository.ChatMessageRepository;

@Service
public class ChatMessageService {
	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private ChatRoomService chatRoomService;

	@Autowired
	private MongoOperations mongoOperations;

	public ChatMessage save(ChatMessage chatMessage) {
		chatMessage.setStatus(MessageStatus.RECEIVED);
		chatMessageRepository.save(chatMessage);
		return chatMessage;
	}

	public long countNewMessages(String senderId, String recipientId) {
		return chatMessageRepository.countBySenderIdAndRecipientIdAndStatus(senderId, recipientId,
				MessageStatus.RECEIVED);
	}

	public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
		Optional<String> chatId = chatRoomService.getChatId(senderId, recipientId, false);
		List<ChatMessage> messages = chatId.map(cId -> chatMessageRepository.findByChatId(cId))
				.orElse(new ArrayList<>());
		if (messages.size() > 0) {
			updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
		}
		return messages;
	}

	public ChatMessage findById(String id) {
		return chatMessageRepository.findById(id).map(chatMessage -> {
			chatMessage.setStatus(MessageStatus.DELIVERED);
			return chatMessageRepository.save(chatMessage);
		}).orElseThrow(() -> new RuntimeException("can't find message (" + id + ")"));
	}

	private void updateStatuses(String senderId, String recipientId, MessageStatus status) {
		Query query = new Query(Criteria.where("senderId").is(senderId).and("recipientId").is(recipientId));
		Update update = Update.update("status", status);
		mongoOperations.updateMulti(query, update, ChatMessage.class);
	}
}
