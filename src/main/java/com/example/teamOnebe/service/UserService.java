package com.example.teamOnebe.service;

import com.example.teamOnebe.entity.Tree;
import com.example.teamOnebe.entity.User;
import com.example.teamOnebe.dto.UserRegisterDto;
import com.example.teamOnebe.repository.TreeRepository;
import com.example.teamOnebe.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final TreeRepository treeRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean register(UserRegisterDto userRegisterDto)
    {
        if(usernameVerify(userRegisterDto.getUsername()))
        {
            return false;
        }
        User user = User.builder()
                    .username(userRegisterDto.getUsername())
                    .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                    .email(userRegisterDto.getEmail())
                    .name(userRegisterDto.getName())
                    .role("ROLE_USER")
                    .build();
        userRepository.save(user);

        Tree tree = Tree.builder()
                .user(user)
                .active(true)
                .build();
        treeRepository.save(tree);
        return true;
    }

    public boolean usernameVerify(String username)
    {
        return userRepository.existsByUsername(username);
    }

    public boolean UpdatePassword(String currentUsername, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(currentUsername)
                .orElse(null);
        if (user == null || !passwordEncoder.matches(oldPassword, user.getPassword()) || newPassword.trim().isEmpty()) {
            return false;
        }
        user.updatePassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    public boolean UpdateName(String currentUsername, String newName) {
        User user = userRepository.findByUsername(currentUsername)
                .orElse(null);
        if (user == null || newName == null || newName.trim().isEmpty()) {
            return false;
        }
        user.updateName(newName);
        userRepository.save(user);
        return true;
    }

    public boolean UpdateAddress(String currentUsername, String newAddress) {
        User user = userRepository.findByUsername(currentUsername)
                .orElse(null);
        if (user == null || newAddress == null || newAddress.trim().isEmpty()) {
            return false;
        }
        user.updateAddress(newAddress);
        userRepository.save(user);
        return true;
    }
}

