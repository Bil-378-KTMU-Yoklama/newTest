package com.example.newTest.repositories;

import com.example.newTest.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findByUsername(String user_login);
    List<UserInfo> findByStatus (String status);
}
