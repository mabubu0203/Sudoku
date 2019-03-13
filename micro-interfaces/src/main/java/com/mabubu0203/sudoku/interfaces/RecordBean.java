package com.mabubu0203.sudoku.interfaces;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 数独の検索結果を保持するBeanです。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class RecordBean {

  private long no;
  private int type;
  private String keyHash;
  private String name;
  private int score;
  private String memo;
  private LocalDate updateDate;
}
