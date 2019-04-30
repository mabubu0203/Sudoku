package com.mabubu0203.sudoku.constants;

import lombok.NoArgsConstructor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static lombok.AccessLevel.PRIVATE;

/**
 * 数独の定数定義クラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor(access = PRIVATE)
public class CommonConstants {

    /**
     * 「a」のUnicodeの文字コード番号を示します。<br>
     *
     * @author uratamanabu
     * @since 1.0
     */
    public static final int ALPHABET_START = 65;

    /**
     * 数値の0を示します。<br>
     *
     * @author uratamanabu
     * @since 1.0
     */
    public static final Integer INTEGER_ZERO = Integer.valueOf(0);

    /**
     * 数値の0を示します。<br>
     *
     * @author uratamanabu
     * @since 1.0
     */
    public static final Long LONG_ZERO = Long.valueOf(0);

    /**
     * Linuxサーバーのデフォルトの文字コードを示します。<br>
     *
     * @author uratamanabu
     * @since 1.0
     */
    public static final Charset UTF8 = StandardCharsets.UTF_8;

    /**
     * 空文字を示します。<br>
     *
     * @author uratamanabu
     * @since 1.0
     */
    public static final String EMPTY_STR = "";

    /**
     * 改行文字を示します。<br>
     *
     * @author uratamanabu
     * @since 1.0
     */
    public static final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * 大文字アルファベットパターンを示します。<br>
     *
     * @author uratamanabu
     * @since 1.0
     */
    public static final String UPPER = "upper";

    /**
     * 小文字アルファベットパターンを示します。<br>
     *
     * @author uratamanabu
     * @since 1.0
     */
    public static final String LOWER = "lower";

    /**
     * [/]を示します。<br>
     *
     * @author uratamanabu
     * @since 1.0
     */
    public static final String SLASH = "/";

}
