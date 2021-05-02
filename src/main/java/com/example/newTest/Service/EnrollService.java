package com.example.newTest.Service;

import com.example.newTest.dto.LessonIdandKod;
import com.example.newTest.dto.StudentRegister;
import com.example.newTest.entity.Enroll;
import com.example.newTest.entity.Lesson;
import com.example.newTest.entity.StateOfEnroll;
import com.example.newTest.entity.Student;
import com.example.newTest.repositories.EnrollRepository;
import com.example.newTest.repositories.LessonRepository;
import com.example.newTest.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface EnrollService{
    List<StudentRegister> getEnrolledStudentsByLesson(LessonIdandKod lesson);
    void enrollStudentToLessons(Student student);
    void enrollLessonToStudents(Lesson lesson);
    void updateState(Integer enrollId, StateOfEnroll state);
}

@Service
class EnrollServiceImpl implements EnrollService {
    @Autowired
    public EnrollRepository enrollRepository;
    @Autowired
    public LessonRepository lessonRepository;
    @Autowired
    public StudentRepository studentRepository;

    @Transactional
    public List<StudentRegister> getEnrolledStudentsByLesson(LessonIdandKod lesson){
        List<StudentRegister> enrolledStudents = new ArrayList<>();
        try {
            System.out.println(lesson);
            Optional<Lesson> lesson1 = lessonRepository.findById(lesson.getId());
            List<Enroll> enrolls;
            if (!lesson1.isPresent()) return null;
            enrolls = enrollRepository.findByLessonId(lesson1.get());
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return enrolledStudents;
    }

    @Transactional
    public void enrollStudentToLessons(Student student){
        List<Lesson> lessonsByFaculty = lessonRepository.findAllByFaculty(student.getFaculty());
        for (Lesson lesson: lessonsByFaculty) {
            Enroll enroll = new Enroll(null, StateOfEnroll.ON_HOLD, student, lesson);
            enrollRepository.save(enroll);
        }
        new ResponseEntity<>("Student enrolled to lessons successfully", HttpStatus.OK);
    }

    @Transactional
    public void enrollLessonToStudents(Lesson lesson){
        List<Student> studentsByFaculty = studentRepository.findAllByFaculty(lesson.getFaculty());
        for (Student student : studentsByFaculty) {
            Enroll enroll = new Enroll(null, StateOfEnroll.ON_HOLD, student, lesson);
            enrollRepository.save(enroll);
        }
    }

    @Transactional
    public void updateState(Integer enrollId, StateOfEnroll state) {
        try {
            Enroll enroll = enrollRepository.findById(enrollId).orElseThrow(ChangeSetPersister.NotFoundException::new);
            enroll.setState(state);
            enrollRepository.save(enroll);
        } catch (ChangeSetPersister.NotFoundException e) {
            e.printStackTrace();
        }
    }
}
