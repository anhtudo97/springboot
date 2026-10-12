package com.springboot;

import com.springboot.entity.user.CCCDEntity;
import com.springboot.entity.user.UserEntity;
import com.springboot.repository.CCCDRepository;
import com.springboot.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class UserCCCDTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CCCDRepository cccdRepository;

    @Test
    @Transactional
    @Rollback(false)
    void oneToOneTest() {
        UserEntity userEntity = new UserEntity();
        CCCDEntity cccdEntity = new CCCDEntity();

        userEntity.setUserName("Tuanh cccd");
        userEntity.setUserEmail("cccd@gmail.com");

        cccdEntity.setNumberCCCD("43432243342432");
        userEntity.setCccd(cccdEntity);

        userRepository.save(userEntity);
    }
}
