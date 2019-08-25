package com.mabubu0203.sudoku.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * パスパラメータ定数クラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor(access = PRIVATE)
public class PathParameterConstants {

    public static final String PATH_TYPE = "{type}";
    // TODO: PATH_ANSWER_KEYに修正する。
    public static final String PATH_TYPEANSWER_KEY = "{answerKey}";

    public static final String PATH_NO = "{no}";

}
