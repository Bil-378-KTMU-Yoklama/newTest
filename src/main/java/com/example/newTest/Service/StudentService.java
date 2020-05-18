package com.example.newTest.Service;

import com.example.newTest.dto.PercentageInfo;
import com.example.newTest.entity.*;
import com.example.newTest.dto.StudentRegister;
import com.example.newTest.repositories.EnrollRepository;
import com.example.newTest.repositories.StudentRepository;
import com.example.newTest.repositories.UserInfoRepository;

import com.example.newTest.repositories.YoklamaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    EnrollRepository enrollRepository;
    @Autowired
    YoklamaRepository yoklamaRepository;

    public ResponseEntity<Object> studentRegisterService(StudentRegister studentRegister, MultipartFile files) throws IOException {
        UserInfo user_info = new UserInfo(null, studentRegister.getKod(),
                bCryptPasswordEncoder.encode(studentRegister.getName()+studentRegister.getKod()),
                "student");
        UserInfo savedUser_info = userInfoRepository.save(user_info);
//        String[] imagesName = new String [5];
//        for(int i = 0; i < 5; i++) {
            String path = "D:\\Aika temp\\Student Image\\" + studentRegister.getKod() +
                    "image" + "1" + ".jpg";
            //imagesName[i] = path;
            File convertFile = new File(path);
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(files.getBytes());
            fout.close();
//        }
        Student st  = new Student(null, studentRegister.getKod(),studentRegister.getName(),
                studentRegister.getSurname(), studentRegister.getFaculty(),
                studentRegister.getDepartment(),savedUser_info, path ,null,
                null, null,null);
        Student tempSt = studentRepository.save(st);
        return new ResponseEntity<>("Student registered successfully", HttpStatus.OK);
    }

    public List<PercentageInfo> getPercentage (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByUsername(currentPrincipalName);
        if (userInfo.getStatus().equals("student")){
            Student student = studentRepository.findByUserId(userInfo);
            List<Enroll> enrolls = enrollRepository.findByStudentId(student);
            List<PercentageInfo> percentageInfos = new ArrayList<>();
            for(int i = 0; i < enrolls.size(); i++){
                Lesson lesson = enrolls.get(i).getLessonId();
                List<Yoklama> yoklamas = yoklamaRepository.findByEnrollId(enrolls.get(i));
                int count =0;
                for (int j = 0; j < yoklamas.size();j++) {
                    if (yoklamas.get(j).getStatus().equals(false)) count++;
                }
                Float percentage = ((float)(100*count)/lesson.getWeek());
                PercentageInfo percentageInfo  = new PercentageInfo(lesson.getKod(), percentage, lesson.getStatus());
                percentageInfos.add(percentageInfo);
            }
            return percentageInfos;
        }
        return null;
    }
}
