package com.example.Dpath.festival;


import com.example.Dpath.config.BaseException;
import com.example.Dpath.config.BaseResponseStatus;
import com.example.Dpath.festival.model.*;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.Dpath.config.BaseResponseStatus.NOT_EXIST_CELEBRITY;
import static com.example.Dpath.config.BaseResponseStatus.NOT_EXIST_UNIVERSITY;

@Service
public class FestivalService {

    private final FestivalProvider festivalProvider;
    private final FestivalRepository festivalRepository;

    @Autowired
    public FestivalService(FestivalProvider festivalProvider, FestivalRepository festivalRepository) {
        this.festivalProvider = festivalProvider;
        this.festivalRepository = festivalRepository;
    }

    public PostFestivalRes createFestival(PostFestivalReq postFestivalReq) throws BaseException {
        try {
            if (festivalRepository.getUnivIdx(postFestivalReq.getUnivName()) == 0) ;
        } catch (Exception e) {
            throw new BaseException(NOT_EXIST_UNIVERSITY);
        }

        try {
            for (Celeb c : postFestivalReq.getCelebs()) {
                if (festivalRepository.getCelebIdx(c) == 0) ;
            }
        } catch (Exception e) {
            throw new BaseException(NOT_EXIST_CELEBRITY);
        }

        try {
            int themeIdx = festivalRepository.getThemeIdx(postFestivalReq.getThemeName());
            int festivalIdx = festivalRepository.createFestival(postFestivalReq.getFestivalName(), postFestivalReq.getStartDate(), postFestivalReq.getEndDate(), postFestivalReq.getUnivName(), themeIdx);
            for (PostImgUrlsReq p : postFestivalReq.getImgUrls()) {
                festivalRepository.updateImgs(festivalIdx, p);

            }

            for (Celeb c : postFestivalReq.getCelebs()) {
                festivalRepository.updateFestival_Celebrity(festivalIdx, c);
            }
            PostFestivalRes postFestivalRes = new PostFestivalRes(festivalIdx, postFestivalReq.getStartDate());

            return postFestivalRes;
        } catch (Exception e) {
            int themeIdx = festivalRepository.getThemeIdx(postFestivalReq.getThemeName());
            System.out.println("festivalRepository.createFestival(postFestivalReq.getFestivalName(), postFestivalReq.getStartDate(), postFestivalReq.getEndDate(), postFestivalReq.getUnivName(), themeIdx); = " + festivalRepository.createFestival(postFestivalReq.getFestivalName(), postFestivalReq.getStartDate(), postFestivalReq.getEndDate(), postFestivalReq.getUnivName(), themeIdx));
                throw new BaseException(BaseResponseStatus.REQUEST_ERROR);
        }
    }

    public void modifyFestival(int festivalIdx, PutFestivalReq putFestivalReq) throws BaseException{
        try {
            if (festivalRepository.getUnivIdx(putFestivalReq.getUnivName()) == 0) ;
        } catch (Exception e) {
            throw new BaseException(NOT_EXIST_UNIVERSITY);
        }

        try {
            for (Celeb c : putFestivalReq.getCelebs()) {
                if (festivalRepository.getCelebIdx(c) == 0) ;
            }
        } catch (Exception e) {
            throw new BaseException(NOT_EXIST_CELEBRITY);
        }



        try {
            if (festivalRepository.getUnivIdx(putFestivalReq.getUnivName()) == 0) ;
        } catch (Exception e) {
            throw new BaseException(NOT_EXIST_UNIVERSITY);
        }


        try {
            int univIdx = festivalRepository.getUnivIdx(putFestivalReq.getUnivName());
            int themeIdx = festivalRepository.getThemeIdx(putFestivalReq.getThemeName());
            festivalRepository.modifyFestival(putFestivalReq.getFestivalName(), putFestivalReq.getStartDate(), putFestivalReq.getEndDate(),putFestivalReq.getUnivName(),putFestivalReq.getLocation(),festivalIdx,univIdx,themeIdx);

            festivalRepository.deleteBeforePut(festivalIdx);

            for (PostImgUrlsReq p : putFestivalReq.getImgUrls()) {
                festivalRepository.modifyImg(festivalIdx, p);
            }
            for (Celeb c : putFestivalReq.getCelebs()) {
                festivalRepository.modifyFestival_Celebrity(festivalIdx, c);
            }


        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.REQUEST_ERROR);
        }
    }

    public void deleteFestival(int festivalIdx) throws BaseException {
        try {
            festivalRepository.patchStatus(festivalIdx);

        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.REQUEST_ERROR);
        }
    }
}
