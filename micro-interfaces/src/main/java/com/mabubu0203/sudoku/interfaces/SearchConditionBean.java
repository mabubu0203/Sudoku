package com.mabubu0203.sudoku.interfaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Digits;
import java.time.LocalDate;

/**
 * 数独の検索条件を保持するBeanです。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SearchConditionBean extends RecordBean {

  @Digits(integer = 1, fraction = 0)
  private int selectorNo;

  @Digits(integer = 1, fraction = 0)
  private int selectorScore;

  @Digits(integer = 1, fraction = 0)
  private int selectorKeyHash;

  @Digits(integer = 1, fraction = 0)
  private int selectorName;

  private LocalDate dateStart;
  private LocalDate dateEnd;
}
