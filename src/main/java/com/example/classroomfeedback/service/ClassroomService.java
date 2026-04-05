package com.example.classroomfeedback.service;

import com.example.classroomfeedback.mapper.ClassroomMapper;
import com.example.classroomfeedback.model.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomMapper classroomMapper;

    // 生成6位唯一教室码
    private String generateUniqueCode() {
        String code;
        do {
            code = RandomStringUtils.randomNumeric(6);
        } while (classroomMapper.findByCode(code) != null);
        return code;
    }

    // 创建课堂
    public Classroom createClassroom(Long teacherId, String title) {
        Classroom classroom = new Classroom();
        classroom.setCode(generateUniqueCode());
        classroom.setTeacherId(teacherId);
        classroom.setTitle(title);
        classroom.setStatus(1); // 进行中
        classroom.setStartTime(new Date());
        classroomMapper.insert(classroom);
        return classroom;
    }

    // 根据教室码获取课堂（并校验状态）
    public Classroom getClassroomByCode(String code) {
        Classroom classroom = classroomMapper.findByCode(code);
        if (classroom == null) {
            throw new RuntimeException("教室码不存在");
        }
        if (classroom.getStatus() != 1) {
            throw new RuntimeException("课堂已结束");
        }
        return classroom;
    }

    // 结束课堂
    public void endClassroom(Long classroomId) {
        Classroom classroom = classroomMapper.findById(classroomId);
        if (classroom != null && classroom.getStatus() == 1) {
            classroom.setStatus(2);
            classroom.setEndTime(new Date());
            classroomMapper.updateStatus(classroom);
        }
    }
}