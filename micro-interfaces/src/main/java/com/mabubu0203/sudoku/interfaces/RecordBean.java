package com.mabubu0203.sudoku.interfaces;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 数独の検索結果を保持するBeanです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class RecordBean {

    @JsonProperty(value = "no")
    private Long no;

    @JsonProperty(value = "type")
    private Integer type;

    @JsonProperty(value = "keyHash")
    private String keyHash;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "score")
    private Integer score;

    @JsonProperty(value = "memo")
    private String memo;

    @JsonProperty(value = "createDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    @JsonProperty(value = "updateDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;

}
