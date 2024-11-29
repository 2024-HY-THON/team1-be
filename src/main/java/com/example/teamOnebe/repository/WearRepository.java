package com.example.teamOnebe.repository;

import com.example.teamOnebe.entity.User;
import com.example.teamOnebe.entity.Wear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WearRepository extends JpaRepository<Wear, Integer> {

    Optional<Wear> findByUser(User user);
}
