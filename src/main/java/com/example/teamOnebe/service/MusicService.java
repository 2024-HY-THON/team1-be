package com.example.teamOnebe.service;

import com.example.teamOnebe.dto.MusicDto;
import com.example.teamOnebe.entity.Music;
import com.example.teamOnebe.entity.User;
import com.example.teamOnebe.repository.MusicRepository;
import com.example.teamOnebe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final UserRepository userRepository;
    private final MusicRepository musicRepository;

    public boolean setMusic(String username, MusicDto musicDto)
    {
        Optional<User> _user = userRepository.findByUsername(username);
        if(_user.isPresent())
        {
            Optional<Music> _music = musicRepository.findByUser(_user.get());
            if(_music.isPresent())
            {
                Music music = _music.get();
                music.updateMusic(musicDto);
                musicRepository.save(music);
                return true;
            }
        }
        return false;
    }

    public MusicDto getMusic(String username)
    {
        Optional<User> _user = userRepository.findByUsername(username);
        if(_user.isPresent())
        {
            Optional<Music> _music = musicRepository.findByUser(_user.get());
            if(_music.isPresent())
            {
                Music music = _music.get();
                return new MusicDto(music.getMusic_id(),music.getTitle(),music.getArtist());
            }
        }
        return null;
    }

}
