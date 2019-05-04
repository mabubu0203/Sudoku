package com.mabubu0203.sudoku.interfaces.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
public class SearchResultBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "no")
    private Long no;

    @JsonProperty(value = "type")
    private Integer type;

    @JsonProperty(value = "answerKey")
    private String answerKey;

    @JsonProperty(value = "keyHash")
    private String keyHash;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "score")
    private Integer score;

    @JsonProperty(value = "memo")
    private String memo;

    @JsonProperty(value = "createDate")
    private LocalDateTime createDate;

}
