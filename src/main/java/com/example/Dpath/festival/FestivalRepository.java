package com.example.Dpath.festival;


import com.example.Dpath.festival.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository
public class FestivalRepository {

    private final JdbcTemplate jdbcTemplate;
    private List<GetImgUrls> imgUrls;

    @Autowired
    public FestivalRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetFestivalRes> getAllFestivalList(){
        String getAllFestivalListQuery = "SELECT f.festivalIdx, fi.imgUrl,\n" +
                "       f.name as festivalName,\n" +
                "       u.name as univName,th.themeName,f.startDate,f.endDate\n" +
                "\n" +
                "FROM Festival as f\n" +
                "         JOIN(SELECT festivalIdx, imgUrl\n" +
                "              FROM ImgUrl\n" +
                "              GROUP BY festivalIdx) fi on fi.festivalIdx = f.festivalIdx\n" +
                "         JOIN(SELECT univIdx, name\n" +
                "              FROM Univ as un) u on u.univIdx = f.univIdx\n" +
                "        JOIN(SELECT themeIdx,themeName\n" +
                "             FROM Theme) th on th.themeIdx = f.themeIdx\n" +
                "WHERE f.status = 'ACTIVE'";

        return jdbcTemplate.query(getAllFestivalListQuery,
                (rs, rowNum) -> new GetFestivalRes(
                        rs.getInt("festivalIdx"),
                        rs.getString("imgUrl"),
                        rs.getString("festivalName"),
                        rs.getString("univName"),
                        rs.getString("themeName"),
                        rs.getString("startDate"),
                        rs.getString("endDate")
                ));
    }


    public GetFestivalByFestivalIdxRes getFestivalInfo(int festivalIdx){

        int getFestivalInfoParam = festivalIdx;
        String getFestivalInfoQuery = "SELECT f.festivalIdx,f.startDate,f.endDate,u.name as univName, u.location ,\n" +
                "       f.name,th.themeName\n" +
                "FROM Festival as f\n" +
                "         JOIN(SELECT festivalIdx, imgUrl\n" +
                "              FROM ImgUrl\n" +
                "              GROUP BY festivalIdx) fi on fi.festivalIdx = f.festivalIdx\n" +
                "         JOIN(SELECT univIdx, name,location\n" +
                "              FROM Univ as un) u on u.univIdx = f.univIdx\n" +
                "         JOIN(SELECT themeIdx,themeName\n" +
                "             FROM Theme) th on th.themeIdx = f.themeIdx\n" +
                "WHERE f.status = 'ACTIVE' and f.festivalIdx =?;";
        return jdbcTemplate.queryForObject(getFestivalInfoQuery,
                (rs, rowNum) -> new GetFestivalByFestivalIdxRes(
                        rs.getInt("festivalIdx"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("univName"),
                        rs.getString("location"),
                        rs.getString("name"),
                        rs.getString("themeName"),
                        imgUrls = this.jdbcTemplate.query("SELECT imgUrlIdx,imgUrl\n" +
                                        "FROM ImgUrl\n" +
                                        "WHERE festivalIdx = ? and status = 'ACTIVE'",
                                (rk, rowNum1) -> new GetImgUrls(
                                        rk.getInt("imgUrlIdx"),
                                        rk.getString("imgUrl")
                                ), rs.getInt("festivalIdx"))


                ), getFestivalInfoParam);
    }

    int createFestival(String festivalName, String date, String univName,String location) {
        Integer univIdx = getUnivIdx(univName);
        String createFestivalQuery = "INSERT INTO Festival(univIdx,date,name) VALUES(?,?,?)";
        Object[] createFestivalParams = {univIdx, date, festivalName};

        jdbcTemplate.update(createFestivalQuery, createFestivalParams);
        return jdbcTemplate.queryForObject("select last_insert_id()", int.class);

    }

    int createUnivIdx(String univName,String location){
        String createUnivIdxQuery = "INSERT INTO Univ(name,location)\n" +
                "VALUES(?,?)";
        String[] getUnivIdxParam = {univName, location};

        jdbcTemplate.update(createUnivIdxQuery, getUnivIdxParam);
        return jdbcTemplate.queryForObject("select last_insert_id()" , int.class);
    }

    public int getUnivIdx(String univName){
        String getUnivIdxQuery = "SELECT univIdx\n" +
                "FROM Univ\n" +
                "WHERE Univ.name=?";
        String getUnivIdxParam = univName;
        return jdbcTemplate.queryForObject(getUnivIdxQuery, int.class, getUnivIdxParam);
    }

    public int updateImgs(int festivalIdx, PostImgUrlsReq postImgUrlsReq) {
        String updateImgsQuery = "INSERT ImgUrl(festivalIdx,imgUrl)\n" +
                "VALUES(?,?)";
        Object[] updateImgsParams = {festivalIdx, postImgUrlsReq.getImgUrl()};
        return jdbcTemplate.update(updateImgsQuery,updateImgsParams);
    }

    public int updateFestival_Celebrity(int festivalIdx, Celeb celeb){
        int celebIdx = getCelebIdx(celeb);

        return jdbcTemplate.update("INSERT Festival_Celebrity(festivalIdx,celebIdx) VALUES(?,?)", festivalIdx, celebIdx);
    }

    public int getCelebIdx(Celeb celeb) {
        String getCelebQuery = "SELECT celebIdx\n" +
                "FROM Celebrity as c\n" +
                "WHERE c.name =?";
        String getCelebParam = celeb.getName();
        int celebIdx = jdbcTemplate.queryForObject(getCelebQuery, int.class, getCelebParam);
        return celebIdx;
    }

    public int patchStatus(int festivalIdx){
        String patchStatusQuery  = "update Festival set status ='INACTIVE' where festivalIdx =?";
        int patchStatusParam = festivalIdx;

        return jdbcTemplate.update(patchStatusQuery, patchStatusParam);
    }

    public int modifyFestival(String festivalName, String startDate, String endDate,String univName,String location,int festivalIdx,int univIdx) {
        String modifyFestivalQuery = "UPDATE Festival set univIdx=?,startDate=?,endDate=?,name=? where festivalIdx = ?";
        Object[] modifyFestivalParams = {univIdx, startDate,endDate, festivalName,festivalIdx};

        jdbcTemplate.update(modifyFestivalQuery, modifyFestivalParams);
        return jdbcTemplate.queryForObject("select last_insert_id()", int.class);

    }

    public void deleteBeforePut(int festivalIdx){
        jdbcTemplate.update("delete from ImgUrl where festivalIdx = ?", festivalIdx);
        jdbcTemplate.update("delete from Festival_Celebrity where festivalIdx = ?", festivalIdx);
    }

    public int modifyImg(int festivalIdx, PostImgUrlsReq postImgUrlsReq) {

        String updateImgsQuery = "INSERT ImgUrl(festivalIdx,imgUrl)\n" +
                "VALUES(?,?)";
        Object[] updateImgsParams = {festivalIdx, postImgUrlsReq.getImgUrl()};
        return jdbcTemplate.update(updateImgsQuery,updateImgsParams);
    }

    public int modifyFestival_Celebrity(int festivalIdx, Celeb celeb){

        int celebIdx = getCelebIdx(celeb);

        return jdbcTemplate.update("INSERT Festival_Celebrity(festivalIdx,celebIdx) VALUES(?,?)", festivalIdx, celebIdx);
    }


}
