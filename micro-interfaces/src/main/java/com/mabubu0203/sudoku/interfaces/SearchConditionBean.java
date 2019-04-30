package com.mabubu0203.sudoku.interfaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 数独の検索条件を保持するBeanです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SearchConditionBean extends RecordBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Digits(integer = 1, fraction = 0)
    private Integer selectorNo;

    @Digits(integer = 1, fraction = 0)
    private Integer selectorScore;

    @Digits(integer = 1, fraction = 0)
    private Integer selectorKeyHash;

    @Digits(integer = 1, fraction = 0)
    private Integer selectorName;

    private LocalDate dateStart;
    private LocalDate dateEnd;

}
