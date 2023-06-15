package com.telegraph.messages.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegraph.messages.models.User;
import com.telegraph.messages.payload.MessageType;
import com.telegraph.messages.payload.request.WebSocketMessageRequest;
import com.telegraph.messages.rep.MessagesRepository;
import com.telegraph.messages.security.services.ChatService;
import com.telegraph.messages.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;
    private Logger logger = Logger.getLogger(WebSocketHandler.class.getName());

    private Map<User, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Autowired
    private MessagesRepository messagesRepository;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        logger.info("Sending message: " + message.getPayload() + " to " + userSessions.size() + " sessions.");
        String payload = message.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        WebSocketMessageRequest obj = mapper.readValue(payload, WebSocketMessageRequest.class);

        User user = obj.getChannelMessage().getSenderUserChannelMessage();
        if (obj.getMessageType().getType().equalsIgnoreCase(MessageType.JOINED.toString())) {
            logger.info(user.getName() + " Joined the chat");
            userSessions.put(user, session);
        } else if (obj.getMessageType().getType().equalsIgnoreCase(MessageType.LEFT.toString())) {
            logger.info(user.getName() + " Left the chat");
            userSessions.remove(user);
        }  else if (obj.getMessageType().getType().equalsIgnoreCase(MessageType.MESSAGE.toString())) {

        }



        for(WebSocketSession webSocketSession : userSessions.values()) {
            webSocketSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("Added Websocket session, total number of sessions are " + userSessions.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("Removed Websocket session, total number of sessions are " + userSessions.size());
    }

    public Set<User> getOnlineUsers() {
        return userSessions.keySet();
    }

}
