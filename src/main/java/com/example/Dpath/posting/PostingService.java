package com.example.Dpath.posting;

import com.example.Dpath.config.BaseException;
import com.example.Dpath.config.BaseResponseStatus;
import com.example.Dpath.posting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.Dpath.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.Dpath.config.BaseResponseStatus.MODIFY_FAIL_POSTING_STATUS;
import static com.example.Dpath.config.BaseResponseStatus.MODIFY_FAIL_POSTING;

@Service
public class PostingService {


    private final PostingDao postingDao;
    private final PostingProvider postingProvider;

    @Autowired
    public PostingService(PostingDao postingDao, PostingProvider postingProvider) {
        this.postingDao = postingDao;
        this.postingProvider = postingProvider;

    }
    // ******************************************************************************

    // 게시물 작성(POST)
    public PostPostingRes createPosting(PostPostingReq postPostingReq) throws BaseException {

        try {
            int postingIdx = postingDao.createPosting(postPostingReq);
            return new PostPostingRes(postingIdx);
        } catch (Exception exception) {
            //5006_DataBase Error입니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }


    /*
     * 게시물 수정
     * [PUT] /postings
     */
    public void modifyPosting(PutPostingReq putPostingReq) throws BaseException {

        try {
            // Diary Table 수정
            if (postingDao.modifyPosting(putPostingReq) == 0) {
                throw new BaseException(MODIFY_FAIL_POSTING);
            } // MODIFY_FAIL_POSTING - 5005- 게시물 수정에 실패하였습니다.

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
            //5006_DataBase Error입니다.
        }
    }

    // 게시물 삭제 - status를 INACTIVE로 변경 (Patch)
    public void modifyPostingStatus(PatchPostingReq patchPostingReq) throws BaseException {
        try {
            int result = postingDao.modifyPostingStatus(patchPostingReq);
            if (result == 0) {
                throw new BaseException(DATABASE_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
            //5006_DataBase Error입니다.
        }
    }


}
