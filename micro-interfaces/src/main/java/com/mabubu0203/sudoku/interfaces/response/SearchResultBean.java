package com.mabubu0203.sudoku.interfaces.response;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SearchResultBean implements Serializable {

  private long no;
  private int type;
  private String answerKey;
  private String keyHash;
  private String name;
  private int score;
  private String memo;
  private LocalDateTime createDate;

}
