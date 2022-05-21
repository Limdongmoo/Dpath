package com.example.Dpath.posting;


import com.example.Dpath.config.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.Dpath.posting.model.*;

import javax.sql.DataSource;
import java.util.List;

import static com.example.Dpath.config.BaseResponseStatus.MODIFY_FAIL_POSTING_STATUS;

@Repository
public class PostingDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 게시물 생성
    public int createPosting(PostPostingReq postPostingReq) {
        String createPostingQuery = "insert into Posting (festivalIdx,personNum, content, postingName) VALUES (?,?,?,?)";
        Object[] createPostingParams = new Object[]{postPostingReq.getFestivalIdx()
                                        ,postPostingReq.getPersonNum()
                                        ,postPostingReq.getContent()
                                        ,postPostingReq.getPostingName()
                                        };
        this.jdbcTemplate.update(createPostingQuery, createPostingParams);

        // 가장 마지막에 삽입된(생성된) blockIdx값을 가져온다.
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // Posting 테이블에 존재하는 전체 게시물들 조회
    public List<GetPostingRes> getPostingList() {

        String getNoticesQuery = "select postingIdx,festivalIdx,personNum,content,postingName from Posting where status = 'active'";
        return this.jdbcTemplate.query(getNoticesQuery,
                (rs, rowNum) -> new GetPostingRes(
                        rs.getInt("postingIdx"),
                        rs.getInt("festivalIdx"),
                        rs.getInt("personNum"),
                        rs.getString("content"),
                        rs.getString("postingName"))
        );
    }

    // Posting 테이블에 존재하는 특정 게시물들 조회
    public GetPostingRes getPosting(int postingIdx) {

        String getPostingQuery = "select postingIdx,festivalIdx,personNum,content,postingName from Posting where postingIdx = ? and status = 'ACTIVE'";

        return this.jdbcTemplate.queryForObject(getPostingQuery,
                (rs, rowNum) -> new GetPostingRes(
                rs.getInt("postingIdx"),
                rs.getInt("festivalIdx"),
                rs.getInt("personNum"),
                rs.getString("content"),
                rs.getString("postingName")), postingIdx);
    }

    // 게시물 수정
    public int modifyPosting(PutPostingReq putPostingReq) {
        String query = "UPDATE Posting SET festivalIdx = ?, personNum = ?, content = ?, postingName = ? WHERE postingIdx = ?";
        Object[] params = new Object[]{putPostingReq.getFestivalIdx(), putPostingReq.getPersonNum(), putPostingReq.getContent(), putPostingReq.getPostingName(), putPostingReq.getPostingIdx()};
        return this.jdbcTemplate.update(query, params);
    }

    // 게시물 삭제 // 차단의 status를 INACTIVE로 변경
    public int modifyPostingStatus(PatchPostingReq patchPostingReq) {
        String modifyStatusQuery = "select status from Posting where postingIdx= ?";
        Object[] modifyStatusParams = new Object[]{patchPostingReq.getPostingIdx()};

        String bool_status = this.jdbcTemplate.queryForObject(modifyStatusQuery, String.class ,modifyStatusParams );


        // Active인 경우 INACTIVE로 상태 변경
        if (bool_status.equals("ACTIVE")) { // JAVA는 String에서 "==" 로 비교 불가능
            String modifyPostingStatusQuery = "update Posting set status = ? where postingIdx = ? ";
            Object[] modifyPostingStatusParams = new Object[]{"INACTIVE", patchPostingReq.getPostingIdx()};

            // 1이 반환됨
            return this.jdbcTemplate.update(modifyPostingStatusQuery, modifyPostingStatusParams);
        } // 이미 INACTIVE일 경우 0을 반환
        else {
            return 0;
        }
    }


}
