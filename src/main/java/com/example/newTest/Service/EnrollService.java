package com.example.newTest.Service;

import com.example.newTest.dto.LessonIdandKod;
import com.example.newTest.dto.StudentRegister;
import com.example.newTest.entity.Enroll;
import com.example.newTest.entity.Lesson;
import com.example.newTest.repositories.EnrollRepository;
import com.example.newTest.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface EnrollService{
    List<StudentRegister> getEnrolledStudentsByLesson(LessonIdandKod lesson);
}

@Service
class EnrollServiceImpl implements EnrollService {
    @Autowired
    public EnrollRepository enrollRepository;
    @Autowired
    public LessonRepository lessonRepository;

    @Transactional
    public List<StudentRegister> getEnrolledStudentsByLesson(LessonIdandKod lesson){
        List<StudentRegister> enrolledStudents = new ArrayList<>();
        try {
            System.out.println(lesson);
            Optional<Lesson> lesson1 = lessonRepository.findById(lesson.getId());
            List<Enroll> enrolls;
            if (lesson1.isPresent()) {
                enrolls = enrollRepository.findByLessonId(lesson1);
                for (Enroll enroll : enrolls) {
                    StudentRegister studentRegister = new StudentRegister();
                    studentRegister.setId(enroll.getStudentId().getId());
                    studentRegister.setKod(enroll.getStudentId().getKod());
                    studentRegister.setName(enroll.getStudentId().getName());
                    studentRegister.setSurname(enroll.getStudentId().getSurname());
                    studentRegister.setFaculty(enroll.getStudentId().getFaculty());
                    studentRegister.setDepartment(enroll.getStudentId().getDepartment());
                    studentRegister.setStudentSemester(enroll.getStudentId().getStudentSemester());
                    enrolledStudents.add(studentRegister);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return enrolledStudents;
    }
}
