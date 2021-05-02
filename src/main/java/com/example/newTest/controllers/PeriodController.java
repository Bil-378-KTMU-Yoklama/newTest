package com.example.newTest.controllers;

import com.example.newTest.Service.PeriodService;
import com.example.newTest.dto.PeriodUpdateRequestDto;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/period")
public class PeriodController {
    @Autowired
    PeriodService periodService;


    @PostMapping("/createOrUpdate")
    public ResponseEntity<Any> setPeriod (@RequestBody PeriodUpdateRequestDto periodDto){
        periodService.createOrUpdate(periodDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
