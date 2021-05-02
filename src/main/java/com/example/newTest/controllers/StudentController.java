package com.example.newTest.controllers;

import com.example.newTest.Service.StudentService;
import com.example.newTest.dto.EnrollSubmitRequestDto;
import com.example.newTest.dto.LessonForEnrollmentResponseDto;
import com.example.newTest.dto.PercentageInfo;
import com.example.newTest.entity.Student;
import com.example.newTest.dto.StudentRegister;
import com.example.newTest.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    public StudentService studentService;
    @Autowired
    public StudentRepository studentRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> registerStudent(@ModelAttribute StudentRegister studentRegister,
                                                  @RequestParam("file") MultipartFile files)
            throws IOException {
        return studentService.studentRegisterService(studentRegister, files);
    }


    @GetMapping("/list")
    public List<Student> studentList() {
        return studentRepository.findAll();
    }

    @GetMapping("student/{id}")
    public Student getById(@PathVariable String id) {
        Integer tempId = Integer.parseInt(id);
        return studentRepository.findById(tempId).orElse(null);
    }

    @GetMapping("/percentage")
    public List<PercentageInfo> getPercentage() {
        return studentService.getPercentage();
    }

    @GetMapping("/listLessons/enrollment")
    public List<LessonForEnrollmentResponseDto> getLessonListForRegistration() {
        return studentService.getLessonListForRegistration();
    }

    @PostMapping("/submit/enrollment")
    public ResponseEntity<Object> submitEnrollment(@RequestBody EnrollSubmitRequestDto enrollList) {
        studentService.submitEnrollment(enrollList);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
