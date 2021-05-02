package com.example.newTest.Service;

import com.example.newTest.constants.PeriodConstants;
import com.example.newTest.dto.EnrollSubmitRequestDto;
import com.example.newTest.dto.LessonForEnrollmentResponseDto;
import com.example.newTest.dto.PercentageInfo;
import com.example.newTest.entity.*;
import com.example.newTest.dto.StudentRegister;
import com.example.newTest.mappers.EnrollMapper;
import com.example.newTest.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    @Autowired
    PeriodRepository periodRepository;
    @Autowired
    EnrollService enrollService;
    @Autowired
    EnrollMapper enrollMapper;


    @Transactional
    public ResponseEntity<Object> studentRegisterService(StudentRegister studentRegister, MultipartFile files) throws IOException {
        UserInfo user_info = new UserInfo(null, studentRegister.getKod(),
                bCryptPasswordEncoder.encode(studentRegister.getName() + studentRegister.getKod()),
                Role.STUDENT);
        UserInfo savedUser_info = userInfoRepository.save(user_info);
//        String[] imagesName = new String [5];
//        for(int i = 0; i < 5; i++) {
        String path = "/home/aidana/Desktop/newTest/D/Aika temp/Student images/" + studentRegister.getKod() +
                "image" + "1" + ".jpg";
        //imagesName[i] = path;
        File convertFile = new File(path);
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(files.getBytes());
        fout.close();
//        }
        Student st = new Student(null, studentRegister.getKod(), studentRegister.getName(),
                studentRegister.getSurname(), studentRegister.getFaculty(),
                studentRegister.getDepartment(), studentRegister.getStudentSemester(), savedUser_info, path, null,
                null, null, null);
        Student tempSt = studentRepository.save(st);

        enrollService.enrollStudentToLessons(tempSt);
        return new ResponseEntity<>("Student registered successfully", HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public List<PercentageInfo> getPercentage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByUsername(currentPrincipalName);
        if (userInfo.getStatus().equals(Role.STUDENT)) {
            Student student = studentRepository.findByUserId(userInfo);
            List<Enroll> enrolls = enrollRepository.findByStudentId(student);
            List<PercentageInfo> percentageInfos = new ArrayList<>();
            for (Enroll enroll : enrolls) {
                Lesson lesson = enroll.getLessonId();
                List<Yoklama> yoklamas = yoklamaRepository.findByEnrollId(enroll);
                int count = 0;
                for (Yoklama yoklama : yoklamas) {
                    if (yoklama.getStatus().equals(false)) count++;
                }
                Float percentage = ((float) (100 * count) / lesson.getWeek());
                PercentageInfo percentageInfo = new PercentageInfo(lesson.getKod(), percentage, lesson.getStatus());
                percentageInfos.add(percentageInfo);
            }
            return percentageInfos;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<LessonForEnrollmentResponseDto> getLessonListForRegistration() {
        if (!periodRepository.findByPurpose(PeriodConstants.REGISTRATION_TO_LESSONS).isPresent())
            return null;

        ZoneId zoneId = ZoneId.of("Asia/Bishkek");
        Period period = periodRepository.findByPurpose(PeriodConstants.REGISTRATION_TO_LESSONS).get();
        ZonedDateTime zonedDateTimeNow = LocalDateTime.now().atZone(zoneId);

        if (zonedDateTimeNow.isAfter(period.getStartDateTime()) && zonedDateTimeNow.isBefore(period.getEndDateTime())) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            UserInfo userInfo = userInfoRepository.findByUsername(currentPrincipalName);

            if (userInfo.getStatus().equals(Role.STUDENT)) {
                Student student = studentRepository.findByUserId(userInfo);
                List<LessonForEnrollmentResponseDto> responseLessonList = new ArrayList<>();
                List<Enroll> allEnrollOfStudent = enrollRepository.findByStudentId(student);
                for (Enroll enroll: allEnrollOfStudent){
                    if (enroll.getLessonId().getSemester().equals(student.getStudentSemester()-2) && student.getStudentSemester() > 2){
                        responseLessonList.add(enrollMapper.enrollToLessonsOfEnrollment(enroll));
                    }
                    if (enroll.getLessonId().getSemester().equals(student.getStudentSemester())){
                        responseLessonList.add(enrollMapper.enrollToLessonsOfEnrollment(enroll));
                    }
                    if (enroll.getLessonId().getSemester().equals(student.getStudentSemester()+2) && student.getStudentSemester() < 7){
                        responseLessonList.add(enrollMapper.enrollToLessonsOfEnrollment(enroll));
                    }
                }
                return responseLessonList;
            }
            else return null;
        } else return null;
    }

    @Transactional
    public void submitEnrollment (EnrollSubmitRequestDto requestDto){
        for (Integer enrollId: requestDto.getList()){
            enrollService.updateState(enrollId, StateOfEnroll.IN_PROCESS);
        }
    }
}
