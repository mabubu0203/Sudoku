package com.mabubu0203.sudoku.rdb.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * {@code answer_info_tbl}のEntityクラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Entity
@Data
@ToString(exclude = {"scoreInfoTbl"})
@Table(
        name = "answer_info_tbl",
        catalog = "sudoku",
        uniqueConstraints = @UniqueConstraint(columnNames = "answerkey")
)
public class AnswerInfoTbl implements Serializable {

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
            name = "type",
            length = 1,
            nullable = false,
            columnDefinition = "TINYINT"
    )
    private Integer type;

    @Column(
            name = "answerkey",
            unique = true,
            length = 81,
            nullable = false,
            columnDefinition = "VARCHAR"
    )
    private String answerKey;

    @Column(
            name = "keyhash",
            unique = true,
            length = 64,
            nullable = false,
            columnDefinition = "VARCHAR"
    )
    private String keyHash;

    @Column(
            name = "create_date",
            columnDefinition = "DATETIME"
    )
    private LocalDateTime createDate;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            targetEntity = ScoreInfoTbl.class
    )
    @JoinColumn(
            name = "no",
            unique = true,
            nullable = false,
            table = "score_info_tbl"
    )
    private ScoreInfoTbl scoreInfoTbl;

}
