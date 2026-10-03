package com.example.springboot.service;

import com.example.springboot.entity.user.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity createUser(UserEntity user);
    List<UserEntity> getUsers();
}
