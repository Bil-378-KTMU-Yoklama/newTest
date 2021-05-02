package com.example.newTest.repositories;

import com.example.newTest.entity.Student;
import com.example.newTest.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByNameAndSurname (String name, String surname);
    Student findByUserId (UserInfo userInfo);
    List<Student> findAllByFaculty (String faculty);
}
