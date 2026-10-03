package com.example.springboot.service.impl;

import com.example.springboot.entity.user.UserEntity;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }
}
