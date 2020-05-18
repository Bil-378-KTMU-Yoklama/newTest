package com.example.newTest.Service;


import com.example.newTest.dto.IdAndStatus;
import com.example.newTest.dto.LessonIdandKod;
import com.example.newTest.dto.LessonRegister;
import com.example.newTest.entity.*;
import com.example.newTest.dto.CoachRegister;
import com.example.newTest.repositories.CoachRepository;
import com.example.newTest.repositories.EnrollRepository;
import com.example.newTest.repositories.LessonRepository;
import com.example.newTest.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoachService {
    @Autowired
    public CoachRepository coachRepository;
    @Autowired
    private UserInfoRepository user_infoRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public LessonRepository lessonRepository;
    @Autowired
    public EnrollRepository enrollRepository;

    public void coachRegister(CoachRegister coachRegister) {
        UserInfo user_info = new UserInfo(null, coachRegister.getLogin(),bCryptPasswordEncoder.encode(coachRegister.getPassword()), "coach");
        UserInfo user_info2 = user_infoRepository.save(user_info);
        Coach coach = new Coach(null, coachRegister.getKod(), coachRegister.getName(), coachRegister.getSurname(), user_info2);
        coachRepository.save(coach);
    }

    public List<LessonIdandKod> getLessons (){
        List<LessonIdandKod> lessonList = new ArrayList<>();
        //System.out.println(idAndStatus);
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            UserInfo userInfo = user_infoRepository.findByUsername(currentPrincipalName);
            if (userInfo.getStatus().equals("coach")){
                Coach coach = coachRepository.findByUserInfo(userInfo);
                List<Lesson> lesson = lessonRepository.findByCoachId(coach);

                for (int i = 0;i < lesson.size(); i++){
                    LessonIdandKod lessonIdandKod = new LessonIdandKod();
                    lessonIdandKod.setId(lesson.get(i).getId());
                    lessonIdandKod.setKod(lesson.get(i).getKod());
                    lessonIdandKod.setStatus(lesson.get(i).getStatus());
                    lessonList.add(lessonIdandKod);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lessonList;
    }

}
