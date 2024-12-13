package com.chatApp.config;

import com.chatApp.model.Chat;
import com.chatApp.model.Message;
import com.chatApp.model.User;
import com.chatApp.repository.ChatRepository;
import com.chatApp.repository.MessageRepository;
import com.chatApp.repository.UserRepository;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    private Map<Long, WebSocketSession> activeSessions = new HashMap<>();
    UserRepository userRepository;
    ChatRepository chatRepository;
    MessageRepository messageRepository;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        String username = session.getPrincipal().getName();
        User user = userRepository.findByLogin(username).orElseThrow();
        activeSessions.put(user.getId(), session);
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String messageContent = message.getPayload();
        String[] messageParts = messageContent.split(":",2);
        Long chatId = Long.parseLong(messageParts[0]);
        String messageText = messageParts[1];

        Chat chat = chatRepository.findById(chatId).orElseThrow();
        User sender = userRepository.findByLogin(session.getPrincipal().getName()).orElseThrow();

        Message newMsg = new Message();
        newMsg.setChat(chat);
        newMsg.setSender(sender);
        newMsg.setTimeStamp(LocalDateTime.now());
        newMsg.setContent(messageText);
        messageRepository.save(newMsg);

        for (User participant : chat.getParticipants()) {
            WebSocketSession participantSession = activeSessions.get(participant.getId());
            if (participantSession != null && participantSession.isOpen()) {
                participantSession.sendMessage(new TextMessage(messageText));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = session.getPrincipal().getName();
        User user = userRepository.findByLogin(username).orElseThrow();
        activeSessions.remove(user.getId());
    }
}
