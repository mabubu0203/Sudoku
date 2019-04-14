package com.mabubu0203.sudoku.interfaces.request;

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

    private int score;

    private String name;

    private String memo;

    private String keyHash;

    @NotNull
    private int type;

}
