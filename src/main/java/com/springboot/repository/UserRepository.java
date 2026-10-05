package com.springboot.repository;

import com.springboot.entity.user.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@RepositoryDefinition(domainClass = UserEntity.class, idClass = Long.class)
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    // find username and userEmail
    // findByUserNameAndUserEmail
    // UserNameAndUserEmail
    // userName included
    // userEmail included
    // where userName = ? and userEmail = ?
    UserEntity findByUserNameAndUserEmail(String userName, String userEmail);

    // userName
    UserEntity findByUserName(String userName);

    /*
     * WHERE userName LIKE '%name'
     * */
    List<UserEntity> findByUserNameStartingWith(String userEmail);

    /*
     * WHERE userName LIKE 'name%'
     * */
    List<UserEntity> findByUserNameEndingWith(String userEmail);

    /*
     * WHERE id < 1
     * */
    List<UserEntity> findByIdLessThan(Long id);

    // RAW JPQL
    @Query("SELECT u FROM UserEntity u WHERE u.id = (SELECT MAX(p.id) FROM UserEntity p)")
    UserEntity findMaxId();

    // RAW JPQL
    @Query("SELECT u FROM UserEntity u WHERE u.userName = ?1 AND u.userEmail = ?2")
    List<UserEntity> getUserEntityBy(String userName, String userEmail);

    // RAW JPQL Another way
    @Query("SELECT u FROM UserEntity u WHERE u.userName = :userName AND u.userEmail = :userEmail")
    List<UserEntity> getUserEntityByTwo(@Param("userName") String userName, @Param("userEmail") String userEmail);

    /*
     * UPDATE DELETE
     * */
    @Modifying
    @Query("UPDATE UserEntity u SET u.userEmail = :userEmail")
    @Transactional
    int updateUserEmail(@Param("userEmail") String userEmail);

    // uncommon not recommend to apply
    /*
     * get count user use native query
     * */
    @Query(value = "SELECT COUNT(id) FROM user", nativeQuery = true)
    long getTotalUser();
}
