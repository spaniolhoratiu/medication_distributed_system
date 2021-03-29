package ro.tuc.ds2020.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;
import ro.tuc.ds2020.websocket.WebSocketMessage;

import java.util.UUID;

@Controller
public class SocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @SendTo("/topic/message")
    public WebSocketMessage sendToAll(@Payload WebSocketMessage message) {
        //WebSocketMessage webSocketMessage = new WebSocketMessage(UUID.randomUUID(), "Test Activity", "Test Message Content From BE");
        simpMessagingTemplate.convertAndSend("/topic/message", message);
        return message;
    }

}