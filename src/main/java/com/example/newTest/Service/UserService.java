package com.example.newTest.Service;

import com.example.newTest.dto.UserInfoSaveDto;
import com.example.newTest.entity.UserInfo;
import com.example.newTest.mappers.UserMapper;
import com.example.newTest.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    UserInfo save (UserInfoSaveDto userInfoDto);
}

@Service
class UserServiceImpl implements UserService {
    @Autowired
    public UserInfoRepository userInfoRepository;
    @Autowired
    public UserMapper userInfoMapper;

    @Transactional
    public UserInfo save (UserInfoSaveDto userInfoDto){
        return userInfoRepository.save(userInfoMapper.userSaveDtoToUserInfoEntity(userInfoDto));
    }
}
