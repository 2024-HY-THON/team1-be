package com.example.teamOnebe.repository;

import com.example.teamOnebe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}