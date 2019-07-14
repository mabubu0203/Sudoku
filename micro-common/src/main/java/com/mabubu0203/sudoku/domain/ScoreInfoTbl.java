package com.mabubu0203.sudoku.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {@code score_info_tbl}のEntityクラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@ToString(exclude = {"answerInfoTbl"})
public class ScoreInfoTbl implements Serializable {

    private static final long serialVersionUID = -5665962434247119049L;

    private Long no;

    private Integer score;

    private String name;

    private String memo;

    private LocalDateTime updateDate;

    private AnswerInfoTbl answerInfoTbl;

}
