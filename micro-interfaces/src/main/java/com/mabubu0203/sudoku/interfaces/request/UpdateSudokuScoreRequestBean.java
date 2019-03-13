package com.mabubu0203.sudoku.interfaces.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateSudokuScoreRequestBean {

  private int score;

  private String name;

  private String memo;

  private String keyHash;

  @NotNull
  private int type;

}
