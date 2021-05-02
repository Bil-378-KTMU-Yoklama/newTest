package com.example.newTest.Service;

import com.example.newTest.dto.PeriodUpdateRequestDto;
import com.example.newTest.entity.Period;
import com.example.newTest.entity.UserInfo;
import com.example.newTest.repositories.PeriodRepository;
import com.example.newTest.repositories.UserInfoRepository;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public interface PeriodService {
    ResponseEntity<Any> createOrUpdate (PeriodUpdateRequestDto requestDto);
}

@Service
class PeriodServiceImpl implements PeriodService{
    ZoneId zoneId = ZoneId.of( "Asia/Bishkek" );

    @Autowired
    public PeriodRepository periodRepository;
    @Autowired
    public UserInfoRepository userInfoRepository;

    public ResponseEntity<Any> createOrUpdate(PeriodUpdateRequestDto requestDto){
        Optional<Period> oldPeriod = periodRepository.findByPurpose(requestDto.getPurpose());

        ZonedDateTime zonedStartDateTime = requestDto.getStartDateTime().atZone(zoneId);
        ZonedDateTime zonedEndDateTime = requestDto.getEndDateTime().atZone(zoneId);
        ZonedDateTime zonedEditedDateTime = LocalDateTime.now().atZone(zoneId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByUsername(currentPrincipalName);

        if(oldPeriod.isPresent()){
            oldPeriod.get().setStartDateTime(zonedStartDateTime);
            oldPeriod.get().setEndDateTime(zonedEndDateTime);
            oldPeriod.get().setEditedDateTime(zonedEditedDateTime);
            oldPeriod.get().setUserInfo(userInfo);
            periodRepository.save(oldPeriod.get());
        }else {
            Period newPeriod = new Period(
                    null,
                    requestDto.getPurpose(),
                    zonedStartDateTime,
                    zonedEndDateTime,
                    zonedEditedDateTime,
                    userInfo
            );
            periodRepository.save(newPeriod);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}