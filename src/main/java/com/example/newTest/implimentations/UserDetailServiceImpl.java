package com.example.newTest.implimentations;

import com.example.newTest.entity.UserInfo;
import com.example.newTest.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserInfoRepository user_infoRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo user_info = user_infoRepository.findByUsername(s);
        if (user_info == null){
            throw new UsernameNotFoundException(s);
        }
        return new User(user_info.getUsername(),user_info.getPassword(), new ArrayList<>());
    }
}
