package com.example.newTest.repositories;

import com.example.newTest.entity.Coach;
import com.example.newTest.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Integer> {
    List<Lesson> findByCoachId (Coach coach);
}
