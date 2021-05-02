package com.example.newTest.repositories;

import com.example.newTest.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Integer> {
    Optional<Period> findByPurpose(String purpose);
}
