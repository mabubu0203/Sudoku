package com.mabubu0203.sudoku.constants;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import static lombok.AccessLevel.PRIVATE;

/**
 * エンドポイント定数クラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor(access = PRIVATE)
public class RdbConstants {

    public static final String URL_ANSWER_INFO_TBL = "answerInfoTbls";
    public static final String URL_SCORE_INFO_TBL = "scoreInfoTbls";

    /**
     * テーブルを列挙型で定義します。<br>
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    public enum Forward {
        ANSWER_INFO_TBL(URL_ANSWER_INFO_TBL),
        SCORE_INFO_TBL(URL_SCORE_INFO_TBL),
        ;

        /**
         * パスを定義します。
         */
        @Getter
        private String path;

        /**
         * コンストラクタ<br>
         *
         * @param value
         * @since 1.0
         */
        Forward(String value) {
            this.path = value;
        }

        /**
         * 列挙型を返却します。<br>
         *
         * @param key
         * @return 難易度
         * @since 1.0
         */
        public static WebUrlConstants.Forward getForward(String key) {
            if (StringUtils.isEmpty(key)) {
                return null;
            }
            for (WebUrlConstants.Forward forward : WebUrlConstants.Forward.values()) {
                if (forward.getPath().equals(key)) {
                    return forward;
                }
            }
            return null;
        }
    }
}
