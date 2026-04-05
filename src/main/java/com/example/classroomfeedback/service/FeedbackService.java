package com.example.classroomfeedback.service;

import com.example.classroomfeedback.mapper.FeedbackMapper;
import com.example.classroomfeedback.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void saveAndBroadcast(String classroomCode, Long classroomId, Long userId, Integer type, String content) {
        // 保存反馈
        Feedback feedback = new Feedback();
        feedback.setClassroomId(classroomId);
        feedback.setUserId(userId);
        feedback.setType(type);
        feedback.setContent(content);
        feedback.setTimestamp(System.currentTimeMillis());
        feedbackMapper.insert(feedback);

        // 构建广播消息
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", type);
        payload.put("content", content);
        payload.put("timestamp", feedback.getTimestamp());
        payload.put("userId", userId);
        // 可选：添加用户昵称（可扩展）

        // 广播到指定教室的主题
        String destination = "/topic/classroom/" + classroomCode;
        messagingTemplate.convertAndSend(destination, payload);
    }
}