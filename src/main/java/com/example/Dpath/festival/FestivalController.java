package com.example.Dpath.festival;


import com.example.Dpath.config.BaseException;
import com.example.Dpath.config.BaseResponse;
import com.example.Dpath.festival.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.Dpath.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/festivals")
public class FestivalController {

    private final FestivalRepository festivalRepository;
    private final FestivalProvider festivalProvider;
    private final FestivalService festivalService;

    @Autowired
    public FestivalController(FestivalRepository festivalRepository, FestivalProvider festivalProvider, FestivalService festivalService) {

        this.festivalRepository = festivalRepository;
        this.festivalProvider = festivalProvider;
        this.festivalService = festivalService;
    }

    @GetMapping("/lists")
    @ResponseBody
    public BaseResponse<GetFestivalListRes> getFestivalList() {
        try {
            GetFestivalListRes getFestivalListRes = festivalProvider.getFestivalList();
            return new BaseResponse<>(getFestivalListRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @GetMapping("")
    @ResponseBody
    public BaseResponse<GetFestivalByFestivalIdxRes> getFestivalByFestivalIdx(@RequestParam("festivalIdx") int festivalIdx) {
        try {
            GetFestivalByFestivalIdxRes getFestivalByFestivalIdxRes = festivalProvider.getFestivalByFestivalIdx(festivalIdx);
            return new BaseResponse<>(getFestivalByFestivalIdxRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("")
    @ResponseBody
    public BaseResponse<PostFestivalRes> createFestival(@RequestBody PostFestivalReq postFestivalReq) {
        try {
            if (postFestivalReq.getFestivalName() == null) {
                return new BaseResponse<>(EMPTY_FESTIVAL_NAME);
            }
            if (postFestivalReq.getUnivName() == null) {
                return new BaseResponse<>(EMPTY_UNIVERSITY_NAME);
            }
            if (postFestivalReq.getLocation() == null) {
                return new BaseResponse<>(EMPTY_LOCATION_NAME);
            }
            if (postFestivalReq.getDate() == null) {
                return new BaseResponse<>(EMPTY_DATE);
            }
            PostFestivalRes postFestivalRes = festivalService.createFestival(postFestivalReq);
            return new BaseResponse<>(postFestivalRes);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PutMapping("/{festivalIdx}")
    @ResponseBody
    public BaseResponse<String> modifyFestival(@PathVariable int festivalIdx, @RequestBody PutFestivalReq putFestivalReq) {
        try {
            festivalService.modifyFestival(festivalIdx, putFestivalReq);
            String result = "수정이 완료되었습니다.";
            return new BaseResponse<>(result);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    @PatchMapping("/{festivalIdx}")
    @ResponseBody

    public BaseResponse<String> deleteFestival(@PathVariable int festivalIdx) {
        try {
            String result = "삭제가 완료되었습니다.";
            festivalService.deleteFestival(festivalIdx);
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
