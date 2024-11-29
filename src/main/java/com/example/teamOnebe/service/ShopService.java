package com.example.teamOnebe.service;

import com.example.teamOnebe.entity.User;
import com.example.teamOnebe.entity.Wear;
import com.example.teamOnebe.repository.UserRepository;
import com.example.teamOnebe.repository.WearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopService {

    private final UserRepository userRepository;
    private final WearRepository wearRepository;

//    public int updateWear(int num)
//    {
//
//    }

    public int getWearNum(String username)
    {
        Optional<User> _user = userRepository.findByUsername(username);
        if(_user.isPresent())
        {
            Optional<Wear> _wear = wearRepository.findByUser(_user.get());
            if(_wear.isPresent())
            {
                return _wear.get().getNum();
            }
        }
        throw new UsernameNotFoundException("User not found");
    }

}
