package com.mabubu0203.sudoku.constants;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class WebUrlConstants {
  public static final String URL_TOP = "top";
  public static final String URL_CHOICE_QUESTION = "choiceQuestion";
  public static final String URL_CREATE_ANSWER = "createAnswer";
  public static final String URL_COMPLETE_ANSWER = "completeAnswer";
  public static final String URL_CREATE_QUESTION = "createQuestion";
  public static final String URL_PLAY_NUMBER_PLACE = "playNumberPlace";
  public static final String URL_BEST_SCORE = "bestScore";
  public static final String URL_BEST_SCORE_COMPLETE = "bestScoreComplete";
  public static final String URL_COMPLETE_NUMBER_PLACE = "completeNumberPlace";
  public static final String URL_SEARCH_ANSWER = "searchAnswer";
  public static final String URL_DETAIL = "detail";
  public static final String URL_INTRODUCE = "introduce";
  public static final String URL_LINK_LIST = "linkList";

  /**
   * ページを列挙型で定義します。
   *
   * @author uratamanabu
   * @version 1.0
   * @since 1.0
   */
  public enum Forward {
    TOP(URL_TOP),
    CHOICE_QUESTION(URL_CHOICE_QUESTION),
    CREATE_ANSWER(URL_CREATE_ANSWER),
    COMPLETE_ANSWER(URL_COMPLETE_ANSWER),
    CREATE_QUESTION(URL_CREATE_QUESTION),
    PLAY_NUMBER_PLACE(URL_PLAY_NUMBER_PLACE),
    BEST_SCORE(URL_BEST_SCORE),
    BEST_SCORE_COMPLETE(URL_BEST_SCORE_COMPLETE),
    COMPLETE_NUMBER_PLACE(URL_COMPLETE_NUMBER_PLACE),
    SEARCH_ANSWER(URL_SEARCH_ANSWER),
    DETAIL(URL_DETAIL),
    INTRODUCE(URL_INTRODUCE),
    LINK_LIST(URL_LINK_LIST),
    ;

    @Getter private String path;

    /** コンストラクタ */
    Forward(String value) {
      this.path = value;
    }

    /**
     * 列挙型を返却します。
     *
     * @author uratamanabu
     * @version 1.0
     * @return　難易度
     * @since 1.0
     */
    public static Forward getForward(String key) {
      if (key == null) {
        return null;
      }
      for (Forward forward : Forward.values()) {
        if (forward.toString().equals(key)) {
          return forward;
        }
      }
      return null;
    }
  }
}
