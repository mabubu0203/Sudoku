package com.mabubu0203.sudoku.interfaces.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
public class UpdateSudokuScoreRequestBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "score")
    private Integer score;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "memo")
    private String memo;

    @JsonProperty(value = "keyHash")
    private String keyHash;

    @NotNull
    @JsonProperty(value = "type")
    private Integer type;

}
