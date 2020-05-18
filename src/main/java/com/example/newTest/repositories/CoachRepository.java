package com.example.newTest.repositories;

import com.example.newTest.entity.Coach;
import com.example.newTest.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Integer> {
    Coach findByUserInfo (UserInfo userInfo);
    Coach findByNameAndSurname (String name, String surname);
}
