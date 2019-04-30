package com.mabubu0203.sudoku.rdb.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ANSWER_INFO_TBLのEntityクラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Entity
@Data
@ToString(exclude = {"scoreInfoTbl"})
@Table(
        name = "ANSWER_INFO_TBL",
        catalog = "SUDOKU",
        uniqueConstraints = @UniqueConstraint(columnNames = "ANSWERKEY")
)
public class AnswerInfoTbl implements Serializable {

    private static final long serialVersionUID = -5665962434247119049L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(
            name = "NO",
            unique = true,
            length = 10,
            nullable = false,
            columnDefinition = "BIGINT"
    )
    private Long no;

    @Column(
            name = "TYPE",
            length = 1,
            nullable = false,
            columnDefinition = "TINYINT"
    )
    private Integer type;

    @Column(
            name = "ANSWERKEY",
            unique = true,
            length = 81,
            nullable = false,
            columnDefinition = "VARCHAR"
    )
    private String answerKey;

    @Column(
            name = "KEYHASH",
            unique = true,
            length = 64,
            nullable = false,
            columnDefinition = "VARCHAR"
    )
    private String keyHash;

    @Column(
            name = "CREATE_DATE",
            columnDefinition = "DATETIME"
    )
    private LocalDateTime createDate;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            targetEntity = ScoreInfoTbl.class
    )
    @JoinColumn(
            name = "NO",
            unique = true,
            nullable = false,
            table = "SCORE_INFO_Tbl"
    )
    private ScoreInfoTbl scoreInfoTbl;

}
