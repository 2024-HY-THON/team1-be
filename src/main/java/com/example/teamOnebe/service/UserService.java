package com.example.teamOnebe.service;

import com.example.teamOnebe.dto.UserDetailsDto;
import com.example.teamOnebe.entity.Music;
import com.example.teamOnebe.entity.Tree;
import com.example.teamOnebe.entity.User;
import com.example.teamOnebe.dto.UserRegisterDto;
import com.example.teamOnebe.entity.Wear;
import com.example.teamOnebe.repository.MusicRepository;
import com.example.teamOnebe.repository.TreeRepository;
import com.example.teamOnebe.repository.UserRepository;
import com.example.teamOnebe.repository.WearRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final TreeRepository treeRepository;
    private final WearRepository wearRepository;
    private final MusicRepository musicRepository;
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

        Wear wear = Wear.builder()
                .user(user)
                .num(0)
                .build();
        wearRepository.save(wear);

        Music music = Music.builder()
                .title("Brisa Do Mar")
                .artist("CTRL S")
                .music_id(1)
                .user(user)
                .build();
        musicRepository.save(music);
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

    public UserDetailsDto getUserDetails(String username) {

        User user = userRepository.findByUsername(username).get();
        if(user != null)
        {
            return new UserDetailsDto(user.getName(), user.getAddress());
        }
        else return null;
    }

    public String getName(String username)
    {
        Optional<User> _user = userRepository.findByUsername(username);
        return _user.map(User::getName).orElse(null);
    }
}

