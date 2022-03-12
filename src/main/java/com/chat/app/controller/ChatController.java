package com.chat.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.chat.app.entity.ChatMessage;
import com.chat.app.entity.ChatNotification;
import com.chat.app.service.ChatMessageService;
import com.chat.app.service.ChatRoomService;

@CrossOrigin
@Controller
public class ChatController {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private ChatMessageService chatMessageService;
	@Autowired
	private ChatRoomService chatRoomService;

	@CrossOrigin
//	@PreAuthorize("permitAll()")
	@MessageMapping("/chat")
	public void processMessage(@Payload ChatMessage chatMessage) {
		Optional<String> chatId = chatRoomService.getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(),
				true);

		chatMessage.setChatId(chatId.get());

		ChatMessage saved = chatMessageService.save(chatMessage);

		messagingTemplate.convertAndSendToUser(chatMessage.getRecipientId(), "/queue/messages",
				new ChatNotification(saved.getId(), saved.getSenderId(), saved.getSenderName()));

	}

	@CrossOrigin
//	@PreAuthorize("permitAll()")
	@GetMapping("/messages/{senderId}/{recipientId}/count")
	public ResponseEntity<Long> countNewMessages(@PathVariable String senderId, @PathVariable String recipientId) {
		return ResponseEntity.ok(chatMessageService.countNewMessages(senderId, recipientId));
	}

	@CrossOrigin
//	@PreAuthorize("permitAll()")
	@GetMapping("/messages/{senderId}/{recipientId}")
	public ResponseEntity<?> findChatMessages(@PathVariable String senderId, @PathVariable String recipientId) {
		return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
	}

	@CrossOrigin
//	@PreAuthorize("permitAll()")
	@GetMapping("/messages/{id}")
	public ResponseEntity<?> findMessage(@PathVariable String id) {
		return ResponseEntity.ok(chatMessageService.findById(id));
	}
}
