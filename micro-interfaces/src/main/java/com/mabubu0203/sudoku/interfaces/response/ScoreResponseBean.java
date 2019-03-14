package com.mabubu0203.sudoku.interfaces.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScoreResponseBean implements Serializable {

    private int score;
    private String name;
    private String memo;

}
