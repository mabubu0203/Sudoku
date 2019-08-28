package com.mabubu0203.sudoku.interfaces.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name = "searchResult", types = {AnswerInfoTbl.class})
public interface SearchResult {

    @Value("#{target.no}")
    Long getNo();

    @Value("#{target.type}")
    Integer getType();

    @Value("#{target.answerKey}")
    String getAnswerKey();

    @Value("#{target.keyHash}")
    String getKeyHash();

    @Value("#{target.scoreInfoTbl.name}")
    String getName();

    @Value("#{target.scoreInfoTbl.score}")
    Integer getScore();

    @Value("#{target.scoreInfoTbl.memo}")
    String getMemo();

    @Value("#{target.createDate}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getCreateDate();

    @Value("#{target.scoreInfoTbl.updateDate}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getUpdateDate();

}
