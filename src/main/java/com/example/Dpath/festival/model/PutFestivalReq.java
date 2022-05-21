package com.example.Dpath.festival.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class PutFestivalReq {
    public PutFestivalReq() {
    }

    private String festivalName;
    private String univName;
    private String location;
    private String startDate;
    private String endDate;
    private List<Celeb> celebs;
    private List<PostImgUrlsReq> imgUrls;

}
