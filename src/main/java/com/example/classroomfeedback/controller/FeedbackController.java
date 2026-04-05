package com.example.classroomfeedback.controller;

import com.example.classroomfeedback.model.Classroom;
import com.example.classroomfeedback.service.ClassroomService;
import com.example.classroomfeedback.service.FeedbackService;
import com.example.classroomfeedback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private UserService userService;

    @MessageMapping("/feedback")
    public void handleFeedback(@Payload Map<String, Object> message) {
        // 消息格式示例:
        // {
        //   "classroomCode": "123456",
        //   "openId": "student001",
        //   "type": 1,        // 1:困惑, 2:弹幕
        //   "content": "这个知识点没听懂"   // 弹幕时必填，困惑时可省略
        // }

        String classroomCode = (String) message.get("classroomCode");
        String openId = (String) message.get("openId");
        Integer type = (Integer) message.get("type");
        String content = (String) message.get("content");

        // 验证课堂存在且进行中
        Classroom classroom = classroomService.getClassroomByCode(classroomCode);
        // 查找或创建用户（如果用户不存在，自动创建）
        // 注意：openId 是微信唯一标识，这里模拟
        com.example.classroomfeedback.model.User user = userService.findOrCreate(openId, "学生", null, 1);

        // 保存并广播
        feedbackService.saveAndBroadcast(classroomCode, classroom.getId(), user.getId(), type, content);
    }
}