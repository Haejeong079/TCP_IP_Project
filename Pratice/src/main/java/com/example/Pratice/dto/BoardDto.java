package com.example.Pratice.dto;

import com.example.Pratice.entity.DashBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {


    private String title;

    private String nickname;

    private String content;

    private LocalDate uploadDate;

    private int viewCount;

    private int commentCount;

    private Long id;


    public DashBoard toEntity() {

        return new DashBoard(id, title, content,nickname, LocalDate.now(), viewCount, commentCount);
    }
}
