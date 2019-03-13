package com.mabubu0203.sudoku.interfaces.request;


import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class SearchSudokuRecordRequestBean {

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
