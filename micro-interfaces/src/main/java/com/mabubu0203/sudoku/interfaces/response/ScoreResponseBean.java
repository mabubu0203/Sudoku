package com.mabubu0203.sudoku.interfaces.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
public class ScoreResponseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "score")
    private Integer score;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "memo")
    private String memo;

}
