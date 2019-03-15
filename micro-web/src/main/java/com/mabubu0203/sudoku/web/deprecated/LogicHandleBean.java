package com.mabubu0203.sudoku.web.deprecated;

import com.mabubu0203.sudoku.form.BaseForm;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.ui.Model;

/**
 * コントローラからロジックへ橋渡しするBeanです。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Deprecated
@Data
@Accessors(chain = true)
public class LogicHandleBean {

    private BaseForm form;
    private Model model;

}
