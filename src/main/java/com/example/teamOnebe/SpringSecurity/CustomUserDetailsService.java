package com.example.teamOnebe.SpringSecurity;

import com.example.teamOnebe.SpringSecurity.jwt.CustomUserDetails;
import com.example.teamOnebe.entity.User;
import com.example.teamOnebe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<User> _user = userRepository.findByUsername(username);
        if(_user.isEmpty())
        {
            throw new UsernameNotFoundException("없는 유저");
        }
        User user = _user.get();

        return new CustomUserDetails(user);
    }
}