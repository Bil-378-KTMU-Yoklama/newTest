package com.example.newTest.mappers;

import com.example.newTest.dto.LessonForEnrollmentResponseDto;
import com.example.newTest.dto.UserInfoSaveDto;
import com.example.newTest.entity.Enroll;
import com.example.newTest.entity.Role;
import com.example.newTest.entity.UserInfo;
import com.example.newTest.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface EnrollMapper {
    LessonForEnrollmentResponseDto enrollToLessonsOfEnrollment (Enroll enroll);
}

@Service
class EnrollMapperImpl implements EnrollMapper {


    @Transactional
    public LessonForEnrollmentResponseDto enrollToLessonsOfEnrollment (Enroll enroll){
        return new LessonForEnrollmentResponseDto(
                enroll.getId(),
                enroll.getLessonId().getId(),
                enroll.getLessonId().getKod(),
                enroll.getLessonId().getSemester(),
                enroll.getState()
        );
    }
}
