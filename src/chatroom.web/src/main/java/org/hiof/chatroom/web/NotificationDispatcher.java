package org.hiof.chatroom.web;

import org.hiof.chatroom.core.ChatMessage;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class NotificationDispatcher {

    private final SimpMessagingTemplate template;

    private Set<String> listeners = new HashSet<>();

    public NotificationDispatcher(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void add(String sessionId) {
        listeners.add(sessionId);
    }

    public void remove(String sessionId) {
        listeners.remove(sessionId);
    }

    public void dispatch(ChatMessage message) {
        for (String listener : listeners) {
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            headerAccessor.setSessionId(listener);
            headerAccessor.setLeaveMutable(true);

//            int value = (int) Math.round(Math.random() * 100d);
            template.convertAndSendToUser(
                    listener,
                    "/topic/chat",
                    message,
                    headerAccessor.getMessageHeaders());
        }
    }

    @EventListener
    public void sessionDisconnectionHandler(String sessionId) {
        remove(sessionId);
    }
}