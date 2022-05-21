package com.example.Dpath.festival;


import com.example.Dpath.config.BaseException;
import com.example.Dpath.config.BaseResponse;
import com.example.Dpath.festival.model.GetFestivalListRes;
import com.example.Dpath.festival.model.GetFestivalRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    @ResponseBody
    public BaseResponse<GetFestivalListRes> getFestivalList() {
        try {
            GetFestivalListRes getFestivalListRes = festivalProvider.getFestivalList();
            return new BaseResponse<>(getFestivalListRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }
}
