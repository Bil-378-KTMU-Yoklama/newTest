package com.example.newTest.Service;

import com.example.newTest.dto.LessonIdandKod;
import com.example.newTest.dto.StudentRegister;
import com.example.newTest.dto.YoklamaNameDate;
import com.example.newTest.entity.Enroll;
import com.example.newTest.entity.Lesson;
import com.example.newTest.entity.Student;
import com.example.newTest.entity.Yoklama;
import com.example.newTest.repositories.EnrollRepository;
import com.example.newTest.repositories.LessonRepository;
import com.example.newTest.repositories.StudentRepository;
import com.example.newTest.repositories.YoklamaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class YoklamaService {
    @Autowired
    private YoklamaRepository yoklamaRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EnrollRepository enrollRepository;

    public ResponseEntity<Object> submit (YoklamaNameDate yoklamaNameDate){
        Optional<Lesson> lesson = lessonRepository.findById(yoklamaNameDate.getLessonId());
        try {
            List<Enroll> enrolls = enrollRepository.findByLessonId(lesson);
            List<Student> students;
            if (yoklamaNameDate.getStudentId().equals(null))  students = new ArrayList<>();
            else  students = studentRepository.findAllById(yoklamaNameDate.getStudentId());
            System.out.println(enrolls);
            System.out.println(students);
            for (int i = 0; i<enrolls.size(); i++){
                Boolean status = false;
                for (int j = 0; j< students.size(); j++){
                    if (enrolls.get(i).getStudentId().equals(students.get(j))){
                        status = true;
                        break;
                    }
                }
                if (status.equals(true)){
                    yoklamaRepository.save(new Yoklama(null, yoklamaNameDate.getName(),
                            yoklamaNameDate.getDate(),true, enrolls.get(i)));
                } else {
                    yoklamaRepository.save(new Yoklama(null, yoklamaNameDate.getName(),
                            yoklamaNameDate.getDate(),false, enrolls.get(i)));
                }
            }
            return new ResponseEntity<>("Yoklama submitted successfully", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Could't find lesson by id, try again", HttpStatus.OK);
    }
}
