package com.sixsixsix516.sunjwt.service;

import com.sixsixsix516.sunjwt.entity.User;
import com.sixsixsix516.sunjwt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User findUser(User user){
        return userMapper.findUser(user);
    }

    public User findUserById(String userId) {
        return userMapper.findUserById(userId);
    }

}
