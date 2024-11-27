package com.example.teamOnebe.repository;

import com.example.teamOnebe.entity.Tree;
import com.example.teamOnebe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TreeRepository extends JpaRepository<Tree, Long> {

    Optional<Tree> findByUserAndActiveIsTrue(User user);
}
