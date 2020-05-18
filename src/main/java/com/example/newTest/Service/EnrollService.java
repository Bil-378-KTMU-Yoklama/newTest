package com.example.newTest.Service;

import com.example.newTest.dto.LessonIdandKod;
import com.example.newTest.dto.StudentRegister;
import com.example.newTest.entity.Enroll;
import com.example.newTest.entity.Lesson;
import com.example.newTest.entity.Student;
import com.example.newTest.repositories.EnrollRepository;
import com.example.newTest.repositories.LessonRepository;
import com.example.newTest.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollService {
    @Autowired
    public EnrollRepository enrollRepository;
    @Autowired
    public LessonRepository lessonRepository;

    public List<StudentRegister> getEnrolledStudentsByLesson(LessonIdandKod lesson){
        List<StudentRegister> enrolledStudents = new ArrayList<>();
        try {
            System.out.println(lesson);
            Optional<Lesson> lesson1 = lessonRepository.findById(lesson.getId());
            List<Enroll> enrolls;
            if (lesson1.isPresent()) {
                enrolls = enrollRepository.findByLessonId(lesson1);
                for (int i = 0; i < enrolls.size(); i++) {
                    StudentRegister studentRegister = new StudentRegister();
                    studentRegister.setId(enrolls.get(i).getStudentId().getId());
                    studentRegister.setKod(enrolls.get(i).getStudentId().getKod());
                    studentRegister.setName(enrolls.get(i).getStudentId().getName());
                    studentRegister.setSurname(enrolls.get(i).getStudentId().getSurname());
                    studentRegister.setFaculty(enrolls.get(i).getStudentId().getFaculty());
                    studentRegister.setDepartment(enrolls.get(i).getStudentId().getDepartment());
                    enrolledStudents.add(studentRegister);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return enrolledStudents;
    }
}
