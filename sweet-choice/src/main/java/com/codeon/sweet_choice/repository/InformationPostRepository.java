package com.codeon.sweet_choice.repository;

import com.codeon.sweet_choice.entity.InformationPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InformationPostRepository extends JpaRepository<InformationPost, Long> {


     // 범위 검색
     // @param start (예: "가")
     // @param end (예: "나")

    @Query("SELECT p FROM InformationPost p JOIN FETCH p.sugar s " +
            "WHERE s.sugarName >= :start AND s.sugarName < :end")
    List<InformationPost> findPostsBySugarNameRange(@Param("start") String start, @Param("end") String end);

     //'하'의 경우  <= '힣' 으로 처리
    @Query("SELECT p FROM InformationPost p JOIN FETCH p.sugar s " +
            "WHERE s.sugarName >= :start AND s.sugarName <= :end")
    List<InformationPost> findPostsBySugarNameRangeLast(@Param("start") String start, @Param("end") String end);
}