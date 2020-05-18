package com.example.newTest.Service;

import com.example.newTest.dto.DepartmentAndFaculty;
import com.example.newTest.dto.LessonRegister;
import com.example.newTest.dto.StudentRegister;
import com.example.newTest.entity.*;
import com.example.newTest.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    EnrollRepository enrollRepository;
    @Autowired
    CoachRepository coachRepository;
    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    UserInfoRepository userInfoRepository;

    public List<StudentRegister> getStudentList (DepartmentAndFaculty departmentAndFaculty){
        String lessonFaculty = departmentAndFaculty.getFaculty();
        String lessonDepartment = departmentAndFaculty.getDepartment();
        List<Student> listStudent = studentRepository.findAll();
        List<StudentRegister> finalList = new ArrayList<>();
        for (Student student : listStudent) {
            if (student.getDepartment().equals(lessonDepartment) &&
                    student.getFaculty().equals(lessonFaculty)) {
                finalList.add(new StudentRegister(student.getId(),student.getKod(),
                        student.getName(), student.getSurname(),student.getFaculty(),
                        student.getDepartment()));
            }
        }
        return finalList;
    }

    public ResponseEntity<Object> setEnroll (LessonRegister lessonRegister){
        System.out.println(lessonRegister);
        Lesson lesson = new Lesson(null, lessonRegister.getKod(), lessonRegister.getFaculty(),
                lessonRegister.getDepartment(), lessonRegister.getWeek(),
                lessonRegister.getStatus(), coachRepository.findById(lessonRegister.getCoach_id()).get());
        Lesson lesson1 = lessonRepository.save(lesson);
        for (Integer student : lessonRegister.getStudents()) {
            Student student1 = studentRepository.findById(student).get();
            Enroll enroll = new Enroll(null, student1, lesson1);
            enrollRepository.save(enroll);
        }
        return new ResponseEntity<>("Lesson registered successfully", HttpStatus.OK);
    }

}
