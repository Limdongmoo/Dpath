package com.example.Dpath.posting.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPostingRes {
    private int postingIdx;  // 게시물 식별자
    private int festivalIdx; // 축제 식별자
    private int personNum; // 모집 인원
    private String content; // 게시물 내용
    private String postingName; // 게시물 이름
    private String imgUrl;
}
