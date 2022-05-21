package com.example.Dpath.festival;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FestivalService {

    private final FestivalProvider festivalProvider;
    private final FestivalRepository festivalRepository;

    @Autowired
    public FestivalService(FestivalProvider festivalProvider, FestivalRepository festivalRepository) {
        this.festivalProvider = festivalProvider;
        this.festivalRepository = festivalRepository;
    }
}
