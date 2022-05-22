package com.example.Dpath.posting;



import com.example.Dpath.config.BaseException;
import com.example.Dpath.config.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Dpath.posting.model.*;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.example.Dpath.config.BaseResponseStatus.POSTING_ERROR;

@RestController
@RequestMapping("/postings")
public class PostingController {
    @Autowired
    private final PostingProvider postingProvider;
    @Autowired
    private final PostingService postingService;

    public PostingController(PostingProvider postingProvider, PostingService postingService) {
        this.postingProvider = postingProvider;
        this.postingService = postingService;
    }


    /**
     * 게시글 작성 API
     * [POST] /postings
     */

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostPostingRes> createPosting(@RequestBody PostPostingReq postPostingReq) {

        try{
            PostPostingRes postPostingRes = postingService.createPosting(postPostingReq);
            return new BaseResponse<>(postPostingRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 게시물 목록 전체 조회 API
     * [GET] /postings
     *
     *
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetPostingRes>> getPostingList() {
        try {
            //조회 성공 시 : List<GetBlocklistRes> 형태로 결과(차단목록) 반환
            List<GetPostingRes> getPostingListRes = postingProvider.getPostingList(); //조회(read) -> Provider
            return new BaseResponse<>(getPostingListRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 게시물 조회 API
     * [GET] /postings?postingIdx=
     *
     * Query String : postingIdx
     */
    @ResponseBody
    @GetMapping("/{postingIdx}")
    public BaseResponse<GetPostingRes> getBlockList(@PathVariable int postingIdx) {
        // @RequestParam을 통해 파라미터를 입력받는다.
        try {
            if (postingIdx == -1) { // query string인 userIdx이 없을 경우, 에러를 방생시킨다.
                return new BaseResponse<>(POSTING_ERROR);
                //POSTING_ERROR : "게시물 조회를 위해 IDX를 입력하세요"  - 5003
            }
            //조회 성공 시 : List<GetBlocklistRes> 형태로 결과(차단목록) 반환
            GetPostingRes getPostingRes = postingProvider.getPosting(postingIdx); //조회(read) -> Provider
            return new BaseResponse<>(getPostingRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 게시물 수정
     * [PUT] /postings
     */
    @ResponseBody
    @PutMapping("")
    public BaseResponse<String> modifyPosting(@RequestBody PutPostingReq putPostingReq) {
        try {
            int postingIdx = putPostingReq.getPostingIdx();

            postingService.modifyPosting(putPostingReq); // 게시물 수정

            String result = "게시물(postingIdx=" + postingIdx + ") 수정 완료";
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시물 삭제 API
     * [PATCH] /postings/:postingIdx
     */
    @ResponseBody
    @PatchMapping("/{postingIdx}")
    // Path-variable - postingIdx를 path-variable로 입력받아 차단의 status를 deleted로 변경
    public BaseResponse<String> modifyPostingStatus(@PathVariable("postingIdx") int postingIdx) {
        try {
            PatchPostingReq patchPostingReq = new PatchPostingReq(postingIdx);
            postingService.modifyPostingStatus(patchPostingReq);
            String result = "게시물이 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }




}
