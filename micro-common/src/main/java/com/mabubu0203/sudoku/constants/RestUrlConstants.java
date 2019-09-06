package com.mabubu0203.sudoku.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * エンドポイント定数クラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor(access = PRIVATE)
public class RestUrlConstants {

    public static final String URL_CREATE_MASTER = "createMaster";
    public static final String URL_SEARCH_MASTER = "searchMaster";
    public static final String URL_UPDATE_MASTER = "updateMaster";
    public static final String URL_GENERATE = "generate";
    public static final String URL_SCORE = "score";
    public static final String URL_SUDOKU = "sudoku";

}
