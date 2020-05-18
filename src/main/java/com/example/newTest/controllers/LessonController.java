package com.example.newTest.controllers;

import com.example.newTest.Service.LessonService;
import com.example.newTest.dto.DepartmentAndFaculty;
import com.example.newTest.dto.LessonRegister;
import com.example.newTest.dto.StudentRegister;
import com.example.newTest.entity.*;
import com.example.newTest.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
public class LessonController {
    @Autowired
    public LessonRepository lessonRepository;

    @Autowired
    public LessonService lessonService;
    @GetMapping("/list")
    public List<Lesson> lessonList (){
        return lessonRepository.findAll();
    }

    @PostMapping("/register1")
    public List<StudentRegister> getStudentList (@RequestBody DepartmentAndFaculty departmentAndFaculty){
        return lessonService.getStudentList(departmentAndFaculty);
    }

    @PostMapping("/register2")
    public ResponseEntity<Object> setEnroll (@RequestBody LessonRegister lessonRegister){
        return lessonService.setEnroll(lessonRegister);
    }

    @GetMapping("/lesson/{id}")
    public Lesson getById(@PathVariable String id){
        Integer tempId = Integer.parseInt(id);
        return lessonRepository.findById(tempId).get();
    }

}
