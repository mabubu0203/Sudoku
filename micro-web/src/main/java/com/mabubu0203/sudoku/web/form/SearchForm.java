package com.mabubu0203.sudoku.web.form;

import com.mabubu0203.sudoku.form.BaseForm;
import com.mabubu0203.sudoku.validator.constraint.KeyHash;
import com.mabubu0203.sudoku.validator.constraint.Name;
import com.mabubu0203.sudoku.validator.constraint.Score;
import com.mabubu0203.sudoku.validator.constraint.Selector;
import com.mabubu0203.sudoku.validator.constraint.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchForm extends BaseForm implements Serializable {

    private static final long serialVersionUID = 8711960650043503815L;

    @NotNull
    @Type(message = "選択肢から選択してください。")
    private int selectType;

    @NotNull
    @Digits(integer = 10, fraction = 0, message = "数値10桁を入力しましょう。")
    private long no;

    @KeyHash(message = "数値64桁を入力しましょう。")
    private String keyHash;

    @NotNull
    @Score(message = "数値7桁を入力しましょう。")
    private int score;

    @Name(message = "64文字までの入力です。")
    private String name;

    private LocalDate dateStart;
    private LocalDate dateEnd;

    @NotNull
    @Selector(message = "改竄値を入力しないでください。")
    private int selectorNo;

    @NotNull
    @Selector(message = "改竄値を入力しないでください。")
    private int selectorKeyHash;

    @NotNull
    @Selector(message = "改竄値を入力しないでください。")
    private int selectorScore;

    @NotNull
    @Selector(message = "改竄値を入力しないでください。")
    private int selectorName;

    @NotNull
    @Digits(integer = 7, fraction = 0)
    private int pageNumber; // ページ番号を取得

    @NotNull
    @Digits(integer = 7, fraction = 0)
    private int pageSize;

}
