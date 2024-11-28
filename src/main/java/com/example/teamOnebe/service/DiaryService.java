package com.example.teamOnebe.service;

import com.example.teamOnebe.dto.DailyEmotion;
import com.example.teamOnebe.dto.DiarySaveDto;
import com.example.teamOnebe.entity.Diary;
import com.example.teamOnebe.entity.Tree;
import com.example.teamOnebe.entity.User;
import com.example.teamOnebe.repository.DiaryRepository;
import com.example.teamOnebe.repository.TreeRepository;
import com.example.teamOnebe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final TreeRepository treeRepository;

    public boolean save(DiarySaveDto dto, String username)
    {
        Optional<User> _user = userRepository.findByUsername(username);
        if(_user.isPresent())
        {
            User user = _user.get();
            Optional<Tree> _tree = treeRepository.findByUserAndActiveIsTrue(user);
            if(_tree.isPresent())
            {
                Tree tree = _tree.get();
                if(tree.getDiaryList().size()==30) //30개인 경우 저장안함
                {
                    return false;
                }

                Diary diary = Diary.builder()
                        .tree(tree)
                        .user(user)
                        .emotion(dto.getEmotion())
                        .type(dto.getType())
                        .content(dto.getContent())
                        .build();
                diaryRepository.save(diary);
                return true;
            }
        }
        return false;
    }

    public List<DailyEmotion> getEmotions(String username)
    {
        Optional<User> _user = userRepository.findByUsername(username);
        if(_user.isPresent())
        {
            User user = _user.get();
            List<Diary> diarys = diaryRepository.findByUserAndCreatedDateBetweenOrderByCreatedDate(user,LocalDate.now().withDayOfMonth(1), LocalDate.now());
            List<DailyEmotion> emotions = new ArrayList<>();
            for(Diary diary : diarys)
            {
                DailyEmotion dailyEmotion = new DailyEmotion(diary.getCreatedDate().getDayOfMonth(), diary.getEmotion());
                emotions.add(dailyEmotion);
            }
            return emotions;
        }
        return null;
    }
}
