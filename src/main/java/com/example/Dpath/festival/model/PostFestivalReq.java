package com.example.Dpath.festival.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class PostFestivalReq {
    public PostFestivalReq() {
    }

    private String festivalName;
    private String univName;
    private String location;
    private String date;
    private List<Celeb> celebs;
    private List<PostImgUrlsReq> imgUrls;

}
