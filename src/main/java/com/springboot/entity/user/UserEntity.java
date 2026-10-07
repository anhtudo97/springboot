package com.springboot.entity.user;

import com.springboot.entity.feed.FeedEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@Entity
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) comment 'user name'", nullable = false)
    private String userName;

    @Column(columnDefinition = "varchar(255) comment 'user email'", nullable = false)
    private String userEmail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedEntity> feedList;
}
