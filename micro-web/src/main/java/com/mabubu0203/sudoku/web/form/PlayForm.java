package com.mabubu0203.sudoku.web.form;

import com.mabubu0203.sudoku.form.BaseForm;
import com.mabubu0203.sudoku.interfaces.NumberPlaceDefine;
import com.mabubu0203.sudoku.validator.constraint.KeyHash;
import com.mabubu0203.sudoku.validator.constraint.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlayForm extends BaseForm implements NumberPlaceDefine, Serializable {

    private static final long serialVersionUID = -521778618347898013L;

    private boolean compareFlg;

    @Digits(integer = 7, fraction = 0)
    private int score;

    @NotNull
    private int count;

    @NotNull
    @Type(message = "選択肢から選択してください。")
    private int type;

    @NotBlank
    @KeyHash(message = "数値64桁を入力しましょう。")
    private String keyHash;

    @Digits(integer = 1, fraction = 0)
    private int a1;

    @Digits(integer = 1, fraction = 0)
    private int a2;

    @Digits(integer = 1, fraction = 0)
    private int a3;

    @Digits(integer = 1, fraction = 0)
    private int a4;

    @Digits(integer = 1, fraction = 0)
    private int a5;

    @Digits(integer = 1, fraction = 0)
    private int a6;

    @Digits(integer = 1, fraction = 0)
    private int a7;

    @Digits(integer = 1, fraction = 0)
    private int a8;

    @Digits(integer = 1, fraction = 0)
    private int a9;

    @Digits(integer = 1, fraction = 0)
    private int b1;

    @Digits(integer = 1, fraction = 0)
    private int b2;

    @Digits(integer = 1, fraction = 0)
    private int b3;

    @Digits(integer = 1, fraction = 0)
    private int b4;

    @Digits(integer = 1, fraction = 0)
    private int b5;

    @Digits(integer = 1, fraction = 0)
    private int b6;

    @Digits(integer = 1, fraction = 0)
    private int b7;

    @Digits(integer = 1, fraction = 0)
    private int b8;

    @Digits(integer = 1, fraction = 0)
    private int b9;

    @Digits(integer = 1, fraction = 0)
    private int c1;

    @Digits(integer = 1, fraction = 0)
    private int c2;

    @Digits(integer = 1, fraction = 0)
    private int c3;

    @Digits(integer = 1, fraction = 0)
    private int c4;

    @Digits(integer = 1, fraction = 0)
    private int c5;

    @Digits(integer = 1, fraction = 0)
    private int c6;

    @Digits(integer = 1, fraction = 0)
    private int c7;

    @Digits(integer = 1, fraction = 0)
    private int c8;

    @Digits(integer = 1, fraction = 0)
    private int c9;

    @Digits(integer = 1, fraction = 0)
    private int d1;

    @Digits(integer = 1, fraction = 0)
    private int d2;

    @Digits(integer = 1, fraction = 0)
    private int d3;

    @Digits(integer = 1, fraction = 0)
    private int d4;

    @Digits(integer = 1, fraction = 0)
    private int d5;

    @Digits(integer = 1, fraction = 0)
    private int d6;

    @Digits(integer = 1, fraction = 0)
    private int d7;

    @Digits(integer = 1, fraction = 0)
    private int d8;

    @Digits(integer = 1, fraction = 0)
    private int d9;

    @Digits(integer = 1, fraction = 0)
    private int e1;

    @Digits(integer = 1, fraction = 0)
    private int e2;

    @Digits(integer = 1, fraction = 0)
    private int e3;

    @Digits(integer = 1, fraction = 0)
    private int e4;

    @Digits(integer = 1, fraction = 0)
    private int e5;

    @Digits(integer = 1, fraction = 0)
    private int e6;

    @Digits(integer = 1, fraction = 0)
    private int e7;

    @Digits(integer = 1, fraction = 0)
    private int e8;

    @Digits(integer = 1, fraction = 0)
    private int e9;

    @Digits(integer = 1, fraction = 0)
    private int f1;

    @Digits(integer = 1, fraction = 0)
    private int f2;

    @Digits(integer = 1, fraction = 0)
    private int f3;

    @Digits(integer = 1, fraction = 0)
    private int f4;

    @Digits(integer = 1, fraction = 0)
    private int f5;

    @Digits(integer = 1, fraction = 0)
    private int f6;

    @Digits(integer = 1, fraction = 0)
    private int f7;

    @Digits(integer = 1, fraction = 0)
    private int f8;

    @Digits(integer = 1, fraction = 0)
    private int f9;

    @Digits(integer = 1, fraction = 0)
    private int g1;

    @Digits(integer = 1, fraction = 0)
    private int g2;

    @Digits(integer = 1, fraction = 0)
    private int g3;

    @Digits(integer = 1, fraction = 0)
    private int g4;

    @Digits(integer = 1, fraction = 0)
    private int g5;

    @Digits(integer = 1, fraction = 0)
    private int g6;

    @Digits(integer = 1, fraction = 0)
    private int g7;

    @Digits(integer = 1, fraction = 0)
    private int g8;

    @Digits(integer = 1, fraction = 0)
    private int g9;

    @Digits(integer = 1, fraction = 0)
    private int h1;

    @Digits(integer = 1, fraction = 0)
    private int h2;

    @Digits(integer = 1, fraction = 0)
    private int h3;

    @Digits(integer = 1, fraction = 0)
    private int h4;

    @Digits(integer = 1, fraction = 0)
    private int h5;

    @Digits(integer = 1, fraction = 0)
    private int h6;

    @Digits(integer = 1, fraction = 0)
    private int h7;

    @Digits(integer = 1, fraction = 0)
    private int h8;

    @Digits(integer = 1, fraction = 0)
    private int h9;

    @Digits(integer = 1, fraction = 0)
    private int i1;

    @Digits(integer = 1, fraction = 0)
    private int i2;

    @Digits(integer = 1, fraction = 0)
    private int i3;

    @Digits(integer = 1, fraction = 0)
    private int i4;

    @Digits(integer = 1, fraction = 0)
    private int i5;

    @Digits(integer = 1, fraction = 0)
    private int i6;

    @Digits(integer = 1, fraction = 0)
    private int i7;

    @Digits(integer = 1, fraction = 0)
    private int i8;

    @Digits(integer = 1, fraction = 0)
    private int i9;

    /**
     * @author uratamanabu
     * @since 1.0
     */
    public void subtractionScore(int num) {

        if (this.score > 0) {
            this.score -= num;
        }
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    public void addCount() {
        this.count++;
    }

}
