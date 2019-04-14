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
    private int selectType;

    @Digits(integer = 10, fraction = 0, message = "数値10桁を入力しましょう。")
    private long no;

    private String keyHash;

    private int score;

    private String name;

    private LocalDate dateStart;
    private LocalDate dateEnd;

    @NotNull
    private int selectorNo;

    @NotNull
    private int selectorKeyHash;

    @NotNull
    private int selectorScore;
    @NotNull
    private int selectorName;

    @NotNull
    @Digits(integer = 7, fraction = 0)
    private int pageNumber;

    @NotNull
    @Digits(integer = 7, fraction = 0)
    private int pageSize;

}
