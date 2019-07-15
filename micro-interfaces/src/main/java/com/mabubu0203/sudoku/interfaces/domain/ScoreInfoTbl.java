package com.mabubu0203.sudoku.interfaces.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * {@code score_info_tbl}のEntityクラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Entity
@Data
@ToString(exclude = {"answerInfoTbl"})
@Table(
        name = "score_info_tbl",
        catalog = "sudoku"
)
public class ScoreInfoTbl implements Serializable {

    private static final long serialVersionUID = -5665962434247119049L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(
            name = "no",
            unique = true,
            length = 10,
            nullable = false,
            columnDefinition = "BIGINT"
    )
    private Long no;

    @Column(
            name = "score",
            length = 7,
            nullable = false,
            columnDefinition = "MEDIUMINT"
    )
    private Integer score;

    @Column(
            name = "name",
            length = 64,
            nullable = false,
            columnDefinition = "VARCHAR"
    )
    private String name;

    @Column(
            name = "memo",
            length = 64,
            nullable = false,
            columnDefinition = "VARCHAR")
    private String memo;

    @Column(
            name = "update_date",
            columnDefinition = "DATETIME"
    )
    private LocalDateTime updateDate;

    @OneToOne(
            mappedBy = "scoreInfoTbl",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            targetEntity = AnswerInfoTbl.class
    )
    @JoinColumn(
            name = "no",
            unique = true,
            nullable = false,
            table = "answer_info_tbl"
    )
    private AnswerInfoTbl answerInfoTbl;

}
