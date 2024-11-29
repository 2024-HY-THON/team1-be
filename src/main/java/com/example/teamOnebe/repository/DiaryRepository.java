package com.example.teamOnebe.repository;

import com.example.teamOnebe.entity.Diary;
import com.example.teamOnebe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByUserAndCreatedDateBetweenOrderByCreatedDate(User user, LocalDate startDate, LocalDate endDate);

    boolean existsByUserAndCreatedDate(User user, LocalDate date);
}
