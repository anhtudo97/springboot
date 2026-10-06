package com.springboot.service;

import com.springboot.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserEntity createUser(UserEntity user);
    List<UserEntity> getUsers();
    UserEntity findByUserNameAndUserEmail(String userName, String userEmail);

    // get all by limit offset
    Page<UserEntity> findAllUsers(Pageable pageable);

    // get search by limit offset
    Page<UserEntity> findByUserName(String userName, Pageable pageable);
}
