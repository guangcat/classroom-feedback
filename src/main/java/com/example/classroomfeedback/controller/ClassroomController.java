package com.example.classroomfeedback.controller;

import com.example.classroomfeedback.model.Classroom;
import com.example.classroomfeedback.model.User;
import com.example.classroomfeedback.service.ClassroomService;
import com.example.classroomfeedback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private UserService userService;

    // 创建课堂（老师端调用）
    @PostMapping("/create")
    public Map<String, Object> createClassroom(@RequestParam Long teacherId, @RequestParam String title) {
        Classroom classroom = classroomService.createClassroom(teacherId, title);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "创建成功");
        result.put("data", classroom);
        return result;
    }

    // 学生加入课堂（验证教室码）
    @PostMapping("/join")
    public Map<String, Object> joinClassroom(@RequestParam String code,
                                             @RequestParam String openId,
                                             @RequestParam(required = false) String nickname) {
        // 验证课堂是否存在且进行中
        Classroom classroom = classroomService.getClassroomByCode(code);
        // 查找或创建学生用户（角色=1）
        User user = userService.findOrCreate(openId, nickname != null ? nickname : "学生", null, 1);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "加入成功");
        result.put("classroom", classroom);
        result.put("user", user);
        return result;
    }

    // 获取课堂信息
    @GetMapping("/{code}")
    public Map<String, Object> getClassroom(@PathVariable String code) {
        Classroom classroom = classroomService.getClassroomByCode(code);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", classroom);
        return result;
    }
}