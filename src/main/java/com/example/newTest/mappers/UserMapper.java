package com.example.newTest.mappers;

import com.example.newTest.dto.UserInfoSaveDto;
import com.example.newTest.entity.Role;
import com.example.newTest.entity.UserInfo;
import com.example.newTest.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface UserMapper {
    UserInfo userSaveDtoToUserInfoEntity (UserInfoSaveDto userInfoDto);
}

@Service
class UserMapperImpl implements UserMapper {

    @Autowired
    public UserInfoRepository user_infoRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserInfo userSaveDtoToUserInfoEntity(UserInfoSaveDto userInfoDto){
        return new UserInfo(
                null,
                userInfoDto.getUsername(),
                bCryptPasswordEncoder.encode(userInfoDto.getPassword()),
                Role.valueOf(userInfoDto.getStatus())
        );
    }
}
