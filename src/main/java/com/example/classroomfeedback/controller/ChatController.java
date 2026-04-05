package com.example.classroomfeedback.controller;

import com.example.classroomfeedback.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")       // 客户端发送到 /app/chat
    @SendTo("/topic/messages")     // 广播到 /topic/messages
    public ChatMessage sendMessage(ChatMessage message) {
        // 可以在这里处理消息，比如记录日志
        return message;
    }
}