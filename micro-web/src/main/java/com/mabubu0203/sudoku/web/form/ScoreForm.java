package com.mabubu0203.sudoku.web.form;

import com.mabubu0203.sudoku.form.BaseForm;
import com.mabubu0203.sudoku.validator.constraint.KeyHash;
import com.mabubu0203.sudoku.validator.constraint.Name;
import com.mabubu0203.sudoku.validator.constraint.Score;
import com.mabubu0203.sudoku.validator.constraint.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ScoreForm extends BaseForm implements Serializable {

    private static final long serialVersionUID = -8386170808653616445L;

    @Score(message = "数値7桁を入力しましょう。")
    private int score;

    @NotNull
    private int count;

    @NotBlank
    @Name(message = "64文字までの入力です。")
    private String name;

    @Size(max = 64)
    private String memo;

    @NotBlank
    @KeyHash(message = "数値64桁を入力しましょう。")
    private String keyHash;

    @NotNull
    @Type(message = "選択肢から選択してください。")
    private int type;

}
