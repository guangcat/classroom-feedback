package com.example.classroomfeedback.service;

import com.example.classroomfeedback.mapper.UserMapper;
import com.example.classroomfeedback.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    // 根据 openId 查找或创建用户（模拟微信登录）
    public User findOrCreate(String openId, String nickname, String avatarUrl, Integer role) {
        User user = userMapper.findByOpenId(openId);
        if (user == null) {
            user = new User();
            user.setOpenId(openId);
            user.setNickname(nickname);
            user.setAvatarUrl(avatarUrl);
            user.setRole(role);
            userMapper.insert(user);
        }
        return user;
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }
}