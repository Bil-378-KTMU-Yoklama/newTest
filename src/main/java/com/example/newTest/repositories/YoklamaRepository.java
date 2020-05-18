package com.example.newTest.repositories;

import com.example.newTest.entity.Enroll;
import com.example.newTest.entity.Yoklama;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YoklamaRepository extends JpaRepository < Yoklama, Integer> {
    List<Yoklama> findByEnrollId(Enroll enroll);
}
