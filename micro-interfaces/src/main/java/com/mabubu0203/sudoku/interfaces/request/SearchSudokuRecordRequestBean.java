package com.mabubu0203.sudoku.interfaces.request;


import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
public class SearchSudokuRecordRequestBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer selectType;

    @Digits(integer = 10, fraction = 0, message = "数値10桁を入力しましょう。")
    private Long no;

    private String keyHash;

    private Integer score;

    private String name;

    private LocalDate dateStart;
    private LocalDate dateEnd;

    @NotNull
    private Integer selectorNo;

    @NotNull
    private Integer selectorKeyHash;

    @NotNull
    private Integer selectorScore;
    @NotNull
    private Integer selectorName;

    @NotNull
    @Digits(integer = 7, fraction = 0)
    private Integer pageNumber;

    @NotNull
    @Digits(integer = 7, fraction = 0)
    private Integer pageSize;

}
