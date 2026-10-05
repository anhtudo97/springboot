package com.springboot.service;

import com.springboot.entity.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserEntity createUser(UserEntity user);
    List<UserEntity> getUsers();
    UserEntity findByUserNameAndUserEmail(String userName, String userEmail);
}
