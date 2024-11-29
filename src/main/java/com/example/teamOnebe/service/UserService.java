package com.example.teamOnebe.service;

import com.example.teamOnebe.dto.UserDetailsDto;
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

    public UserDetailsDto getUserDetails(String username) {

        User user = userRepository.findByUsername(username).get();
        if(user != null)
        {
            return new UserDetailsDto(user.getName(), user.getAddress());
        }
        else return null;

    }
}
