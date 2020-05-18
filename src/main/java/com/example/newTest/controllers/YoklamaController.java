package com.example.newTest.controllers;

import com.example.newTest.Service.YoklamaService;
import com.example.newTest.dto.YoklamaNameDate;
import com.example.newTest.entity.Yoklama;
import com.example.newTest.repositories.YoklamaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/yoklama")
public class YoklamaController {
    @Autowired
    public YoklamaRepository yoklamaRepository;
    @Autowired
    public YoklamaService yoklamaService;

    @PostMapping ("/submit")
    public ResponseEntity<Object> enroll (@RequestBody YoklamaNameDate yoklamaNameDate){
        return yoklamaService.submit(yoklamaNameDate);
    }
    @GetMapping ("/list")
    public List<Yoklama> yoklamaList (){
        return yoklamaRepository.findAll();
    }
}
