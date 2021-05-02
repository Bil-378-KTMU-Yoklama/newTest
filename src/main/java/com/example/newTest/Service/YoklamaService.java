package com.example.newTest.Service;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface YoklamaService {
    ResponseEntity<Object> submit(YoklamaNameDate yoklamaNameDate);
}

@Service
class YoklamaServiceImpl implements YoklamaService{
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
            if (!lesson.isPresent()) return new ResponseEntity<>("Lesson is not found!", HttpStatus.NOT_FOUND);
            List<Enroll> enrolls = enrollRepository.findByLessonId(lesson.get());
            List<Student> students;
            if (yoklamaNameDate.getStudentId().isEmpty())  students = new ArrayList<>();
            else  students = studentRepository.findAllById(yoklamaNameDate.getStudentId());
            System.out.println(enrolls);
            System.out.println(students);
            for (Enroll enroll : enrolls) {
                Boolean status = false;
                for (Student student : students) {
                    if (enroll.getStudentId().equals(student)) {
                        status = true;
                        break;
                    }
                }
                if (status.equals(true)) {
                    yoklamaRepository.save(new Yoklama(null, yoklamaNameDate.getName(),
                            yoklamaNameDate.getDate(), true, enroll));
                } else {
                    yoklamaRepository.save(new Yoklama(null, yoklamaNameDate.getName(),
                            yoklamaNameDate.getDate(), false, enroll));
                }
            }
            return new ResponseEntity<>("Yoklama submitted successfully", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Couldn't find lesson by id, try again", HttpStatus.OK);
    }
}