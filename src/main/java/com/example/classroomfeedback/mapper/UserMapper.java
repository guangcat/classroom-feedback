package com.example.classroomfeedback.mapper;

import com.example.classroomfeedback.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (open_id, nickname, avatar_url, role) VALUES (#{openId}, #{nickname}, #{avatarUrl}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT * FROM user WHERE open_id = #{openId}")
    User findByOpenId(@Param("openId") String openId);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") Long id);
}