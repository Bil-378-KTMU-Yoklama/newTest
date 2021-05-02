package com.example.newTest.controllers;

import com.example.newTest.Service.UserService;
import com.example.newTest.dto.UserInfoSaveDto;
import com.example.newTest.entity.UserInfo;
import com.example.newTest.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    public UserInfoRepository user_infoRepository;
    @Autowired
    public UserService userInfoService;

    @GetMapping("/list")
    public List<UserInfo> user_infoList() {
        return user_infoRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public UserInfo show(@PathVariable String id) {
        Integer newId = Integer.parseInt(id);
        return user_infoRepository.findById(newId).orElse(null);
    }

    @PostMapping("/save")
    public UserInfo save(@RequestBody UserInfoSaveDto userDto) {
        return userInfoService.save(userDto);
    }
}
