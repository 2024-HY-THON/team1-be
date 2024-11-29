package com.example.teamOnebe.service;

import com.example.teamOnebe.entity.User;
import com.example.teamOnebe.repository.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageRepository myPageRepository;

    // 이름 변경 로직
    public boolean updateName(String username, String newName) {
        User user = myPageRepository.findByUsername(username);
        if (user != null) {
            user.setName(newName);
            myPageRepository.save(user);
            return true;
        }
        return false;
    }

    // 비밀번호 변경 로직
    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        User user = myPageRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(oldPassword)) { // 단순 비교, 실제로는 암호화 필요
            user.setPassword(newPassword);
            myPageRepository.save(user);
            return true;
        }
        return false;
    }

    // 주소 변경 로직
    public boolean updateAddress(String username, String newAddress) {
        User user = myPageRepository.findByUsername(username);
        if (user != null) {
            user.setAddress(newAddress);
            myPageRepository.save(user);
            return true;
        }
        return false;
    }
}