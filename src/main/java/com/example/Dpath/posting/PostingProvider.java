package com.example.Dpath.posting;


import com.example.Dpath.config.BaseException;
import com.example.Dpath.config.BaseResponseStatus;
import com.example.Dpath.posting.model.GetPostingRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.Dpath.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PostingProvider {
    private final PostingDao postingDao;

    @Autowired
    public PostingProvider(PostingDao postingDao) {
        this.postingDao = postingDao;
    }

    // 모든 게시물 조회
    public List<GetPostingRes> getPostingList() throws BaseException {
        try {
            List<GetPostingRes> getPostingListRes = postingDao.getPostingList();
            return getPostingListRes;
        } catch (Exception exception) {
            // 에러가 발생하였다면 : 5006 : DataBase Error입니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 특정 게시물 조회
    public GetPostingRes getPosting(int postingIdx) throws BaseException {
        try {
            GetPostingRes getPostingRes = postingDao.getPosting(postingIdx);
            return getPostingRes;
        } catch (Exception exception) {
            System.out.println("postingDao.getPosting(postingIdx) = " + postingDao.getPosting(postingIdx));

            // 에러가 발생하였다면 : 5006 : DataBase Error입니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
