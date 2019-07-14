package com.mabubu0203.sudoku.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {@code answer_info_tbl}のEntityクラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@ToString(exclude = {"scoreInfoTbl"})
public class AnswerInfoTbl implements Serializable {

    private static final long serialVersionUID = -5665962434247119049L;

    private Long no;

    private Integer type;

    private String answerKey;

    private String keyHash;

    private LocalDateTime createDate;

    private ScoreInfoTbl scoreInfoTbl;

}
