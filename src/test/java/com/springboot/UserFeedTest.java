package com.springboot;

import com.springboot.entity.feed.FeedEntity;
import com.springboot.entity.user.UserEntity;
import com.springboot.repository.FeedRepository;
import com.springboot.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
public class UserFeedTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedRepository feedRepository;

    @Test
    @Transactional
    @Rollback(false)
    void oneToManyTest(){
        // New user
        UserEntity user = new UserEntity();
        FeedEntity feed = new FeedEntity();

        user.setUserName("tuanh");
        user.setUserEmail("tuanh@gmail.com");

        feed.setTitle("feed 1");
        feed.setDescription("feed 1");
        user.setFeedList(List.of(feed));
        feed.setUser(user);

//        feedRepository.save(feed);
        userRepository.save(user);
    }
}
