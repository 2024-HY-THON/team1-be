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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final TreeRepository treeRepository;

//    public boolean makeTestDiary(String username)
//    {
//        User user = userRepository.findByUsername(username).get();
//        Tree tree = treeRepository.findByUserAndActiveIsTrue(user).get();
//
//        // Diary 데이터 생성
//        List<Diary> diaries = Arrays.asList(
//                Diary.builder().user(user).tree(tree).createdDate(LocalDate.of(2024, 10, 1)).emotion("good").type("happy").content("Test content 1").build(),
//                Diary.builder().user(user).tree(tree).createdDate(LocalDate.of(2024, 10, 2)).emotion("soso").type("worry").content("Test content 2").build(),
//                Diary.builder().user(user).tree(tree).createdDate(LocalDate.of(2024, 11, 3)).emotion("bad").type("sad").content("Test content 3").build()
//        );
//
//        // 데이터베이스에 저장
//        diaryRepository.saveAll(diaries);
//        return true;
//    }



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

    /**
     * @return 지정된 년 월 감정목록
     */
    public List<DailyEmotion> getEmotionsByYearMonth(String username, Long year, Long month)
    {
        Optional<User> _user = userRepository.findByUsername(username);
        if(_user.isPresent())
        {
            LocalDate startDate = LocalDate.of(year.intValue(), month.intValue(), 1); // 해당 월의 첫째 날
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth()); // 해당 월의 마지막 날

            User user = _user.get();

            List<Diary> diarys = diaryRepository.findByUserAndCreatedDateBetweenOrderByCreatedDate(user, startDate,  endDate);

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

    /**
     * @return 이번 달 감정목록
     */
    public List<DailyEmotion> getEmotions(String username)
    {
        Optional<User> _user = userRepository.findByUsername(username);
        if(_user.isPresent())
        {
            LocalDate startDate = LocalDate.now().withDayOfMonth(1);
            LocalDate endDate = LocalDate.now();

            User user = _user.get();

            List<Diary> diarys = diaryRepository.findByUserAndCreatedDateBetweenOrderByCreatedDate(user, startDate, endDate);

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
