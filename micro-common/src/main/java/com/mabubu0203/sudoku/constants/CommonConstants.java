package com.mabubu0203.sudoku.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 数独の定数定義クラスです。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public class CommonConstants {

  /**
   * 「a」のUnicodeの文字コード番号を示します。
   *
   * @author uratamanabu
   * @version 1.0
   * @since 1.0
   */
  public static final int ALPHABET_START = 65;
  /**
   * 数値の0を示します。
   *
   * @author uratamanabu
   * @version 1.0
   * @since 1.0
   */
  public static final int ZERO = 0;
  /**
   * Linuxサーバーのデフォルトの文字コードを示します。
   *
   * @author uratamanabu
   * @version 1.0
   * @since 1.0
   */
  public static final Charset UTF8 = StandardCharsets.UTF_8;
  /**
   * 空文字を示します。
   *
   * @author uratamanabu
   * @version 1.0
   * @since 1.0
   */
  public static final String EMPTY_STR = "";
  /**
   * 改行文字を示します。
   *
   * @author uratamanabu
   * @version 1.0
   * @since 1.0
   */
  public static final String LINE_SEPARATOR = System.lineSeparator();
  /**
   * 大文字アルファベットパターンを示します。
   *
   * @author uratamanabu
   * @version 1.0
   * @since 1.0
   */
  public static final String UPPER = "upper";
  /**
   * 小文字アルファベットパターンを示します。
   *
   * @author uratamanabu
   * @version 1.0
   * @since 1.0
   */
  public static final String LOWER = "lower";

  public static final String SLASH = "/";

  /**
   * デフォルトコンストラクタ生成禁止です。
   *
   * @author uratamanabu
   * @version 1.0
   * @since 1.0
   */
  private CommonConstants() {
    throw new IllegalAccessError("Constant class");
  }
}
