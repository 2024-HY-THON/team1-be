package com.example.teamOnebe.service;

import com.example.teamOnebe.entity.Tree;
import com.example.teamOnebe.entity.User;
import com.example.teamOnebe.repository.TreeRepository;
import com.example.teamOnebe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TreeService {

    private final TreeRepository treeRepository;
    private final UserRepository userRepository;

    public int getTreeDiaryNum(String username)
    {
        Optional<User> _user = userRepository.findByUsername(username);
        if(_user.isPresent())
        {
            Optional<Tree> _tree = treeRepository.findByUserAndActiveIsTrue(_user.get());
            if(_tree.isPresent())
            {
                return _tree.get().getDiaryList().size();
            }
        }
        //user or tree가 없는경우에 대한 예외처리 필요
        return 0;
    }
}
