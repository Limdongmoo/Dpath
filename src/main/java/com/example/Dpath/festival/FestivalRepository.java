package com.example.Dpath.festival;


import com.example.Dpath.festival.model.GetFestivalRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class FestivalRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FestivalRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetFestivalRes> getAllFestivalList(){
        String getAllFestivalListQuery = "SELECT f.festivalIdx,f.name,fi.imgUrl\n" +
                "FROM Festival as f\n" +
                "    JOIN(SELECT festivalIdx,imgUrl\n" +
                "         FROM ImgUrl\n" +
                "         GROUP BY festivalIdx) fi on fi.festivalIdx = f.festivalIdx\n" +
                "WHERE f.status = 'ACTIVE'";

        return jdbcTemplate.query(getAllFestivalListQuery,
                (rs, rowNum) -> new GetFestivalRes(
                        rs.getInt("festivalIdx"),
                        rs.getString("imgUrl")
                ));


    }
}
