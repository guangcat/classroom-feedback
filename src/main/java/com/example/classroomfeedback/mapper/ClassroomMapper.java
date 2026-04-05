package com.example.classroomfeedback.mapper;

import com.example.classroomfeedback.model.Classroom;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ClassroomMapper {

    @Insert("INSERT INTO classroom (code, teacher_id, title, status) VALUES (#{code}, #{teacherId}, #{title}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Classroom classroom);

    @Select("SELECT * FROM classroom WHERE code = #{code}")
    Classroom findByCode(@Param("code") String code);

    @Select("SELECT * FROM classroom WHERE id = #{id}")
    Classroom findById(@Param("id") Long id);

    @Update("UPDATE classroom SET status = #{status}, end_time = #{endTime} WHERE id = #{id}")
    int updateStatus(Classroom classroom);
}