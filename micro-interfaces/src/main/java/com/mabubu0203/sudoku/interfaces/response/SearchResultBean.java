package com.mabubu0203.sudoku.interfaces.response;

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

    private long no;
    private int type;
    private String answerKey;
    private String keyHash;
    private String name;
    private int score;
    private String memo;
    private LocalDateTime createDate;

}
