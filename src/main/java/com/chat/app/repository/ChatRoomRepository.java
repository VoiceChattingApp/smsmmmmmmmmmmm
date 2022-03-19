package com.chat.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chat.app.entity.ChatRoom;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
	Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);

}
