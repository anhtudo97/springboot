package com.springboot.repository;

import com.springboot.entity.user.CCCDEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CCCDRepository extends JpaRepository<CCCDEntity, Long> {
}
