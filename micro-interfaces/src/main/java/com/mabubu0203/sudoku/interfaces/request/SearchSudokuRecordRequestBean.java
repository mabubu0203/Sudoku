package com.mabubu0203.sudoku.interfaces.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value = "selectType")
    private Integer selectType;

    @Digits(integer = 10, fraction = 0, message = "数値10桁を入力しましょう。")
    @JsonProperty(value = "no")
    private Long no;

    @JsonProperty(value = "keyHash")
    private String keyHash;

    @JsonProperty(value = "score")
    private Integer score;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "dateStart")
    private LocalDate dateStart;

    @JsonProperty(value = "dateEnd")
    private LocalDate dateEnd;

    @NotNull
    @JsonProperty(value = "selectorNo")
    private Integer selectorNo;

    @NotNull
    @JsonProperty(value = "selectorKeyHash")
    private Integer selectorKeyHash;

    @NotNull
    @JsonProperty(value = "selectorScore")
    private Integer selectorScore;

    @NotNull
    @JsonProperty(value = "selectorName")
    private Integer selectorName;

    @NotNull
    @Digits(integer = 7, fraction = 0)
    @JsonProperty(value = "pageNumber")
    private Integer pageNumber;

    @NotNull
    @Digits(integer = 7, fraction = 0)
    @JsonProperty(value = "pageSize")
    private Integer pageSize;

}
