package com.example.hyppp.service;

import com.example.hyppp.entity.User;
import com.example.hyppp.dto.UserRegisterDto;
import com.example.hyppp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
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
        return true;
    }

    public boolean usernameVerify(String username)
    {
        return userRepository.existsByUsername(username);
    }
}
