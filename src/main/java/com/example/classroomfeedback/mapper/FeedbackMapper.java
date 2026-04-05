package com.example.classroomfeedback.mapper;

import com.example.classroomfeedback.model.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FeedbackMapper {

    @Insert("INSERT INTO feedback (classroom_id, user_id, type, content, timestamp) " +
            "VALUES (#{classroomId}, #{userId}, #{type}, #{content}, #{timestamp})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Feedback feedback);
}