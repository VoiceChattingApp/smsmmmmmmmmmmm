package com.chat.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.chat.app.entity.ChatNotification;

@Component
public class WebSocketEventListener {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
    
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    	StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("id");
        if(username != null) {
        	messagingTemplate.convertAndSendToUser(username, "/queue/messages", new ChatNotification("LEAVE", username, null));
        }
    }
}
