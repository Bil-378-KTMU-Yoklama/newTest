package com.example.newTest.controllers;

import com.example.newTest.Service.EnrollService;
import com.example.newTest.dto.LessonIdandKod;
import com.example.newTest.dto.StudentRegister;
import com.example.newTest.entity.Enroll;
import com.example.newTest.repositories.EnrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enroll")
public class EnrollController {
    @Autowired
    public EnrollRepository enrollRepository;
    @Autowired
    public EnrollService enrollService;

    @GetMapping("/list")
    public List<Enroll> getEnrollList (){
        return enrollRepository.findAll();
    }

    @PostMapping("/getStudents")
    public List<StudentRegister> getEnrolledStudentsByLesson (@RequestBody LessonIdandKod lesson){
        return enrollService.getEnrolledStudentsByLesson(lesson);
    }
}
