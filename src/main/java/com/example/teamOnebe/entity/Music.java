package com.example.teamOnebe.entity;

import com.example.teamOnebe.dto.MusicDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    private User user;

    @Column(nullable = false)
    private int music_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    public void updateMusic(MusicDto musicDto)
    {
        this.music_id = musicDto.getMusic_id();
        this.title = musicDto.getTitle();
        this.artist = musicDto.getArtist();
    }

}
