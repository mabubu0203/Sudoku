package com.mabubu0203.sudoku.web.form;

import com.mabubu0203.sudoku.form.BaseForm;
import com.mabubu0203.sudoku.validator.constraint.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DetailForm extends BaseForm implements Serializable {

    private static final long serialVersionUID = -2616500675152025057L;

    @NotNull
    @Type(message = "選択肢から選択してください。")
    private int type;

    @NotBlank
    @Pattern(regexp = "easy|normal|hard", message = "選択肢から選択してください。")
    private String selectLevel;

    @NotBlank
    @Size(max = 64, message = "keyHashは64文字までです。")
    private String keyHash;

}
