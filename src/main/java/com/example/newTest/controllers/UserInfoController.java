package com.example.newTest.controllers;
import com.example.newTest.dto.UsernamePassword;
import com.example.newTest.entity.UserInfo;
import com.example.newTest.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    public UserInfoRepository user_infoRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/list")
    public List<UserInfo> user_infoList (){
        return user_infoRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public UserInfo show(@PathVariable String id){
        Integer newId = Integer.parseInt(id);
        return user_infoRepository.findById(newId).get();
    }

    @PostMapping("/save")
    public UserInfo save (@RequestBody UserInfo user_info){
        user_info.setPassword(bCryptPasswordEncoder.encode(user_info.getPassword()));
        return user_infoRepository.save(user_info);
    }
}
