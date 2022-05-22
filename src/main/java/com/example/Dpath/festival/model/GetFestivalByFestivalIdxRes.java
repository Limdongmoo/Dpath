package com.example.Dpath.festival.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class GetFestivalByFestivalIdxRes {
    private int festivalIdx;
    private String startDate;
    private String endDate;
    private String univName;
    private String location;
    private String festivalName;
    private String themeName;
    private String imgUrl;
}
