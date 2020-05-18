package com.example.newTest.repositories;

import com.example.newTest.entity.Enroll;
import com.example.newTest.entity.Lesson;
import com.example.newTest.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Integer> {
    List<Enroll> findByLessonId (Optional<Lesson> lesson);
    Enroll findByLessonIdAndStudentId (Lesson lesson, Student student);
    List<Enroll> findByStudentId (Student student);
}
