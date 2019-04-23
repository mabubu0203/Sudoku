package com.mabubu0203.sudoku.interfaces.response;

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

    private Integer score;
    private String name;
    private String memo;

}
