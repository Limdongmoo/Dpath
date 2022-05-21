package com.example.Dpath.festival;

import com.example.Dpath.config.BaseException;
import com.example.Dpath.config.BaseResponseStatus;
import com.example.Dpath.festival.model.GetFestivalByFestivalIdxRes;
import com.example.Dpath.festival.model.GetFestivalListRes;
import com.example.Dpath.festival.model.GetFestivalRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FestivalProvider {
    private final FestivalRepository festivalRepository;

    @Autowired
    public FestivalProvider(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }

    public GetFestivalListRes getFestivalList() throws BaseException {

        try {
            List<GetFestivalRes> festivalList = festivalRepository.getAllFestivalList();
            GetFestivalListRes getFestivalListRes = new GetFestivalListRes(festivalList);
            return getFestivalListRes;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }


    }

    public GetFestivalByFestivalIdxRes getFestivalByFestivalIdx(int festivalIdx) throws BaseException {
        try {
            GetFestivalByFestivalIdxRes getFestivalByFestivalIdxRes = festivalRepository.getFestivalInfo(festivalIdx);
            return getFestivalByFestivalIdxRes;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}
