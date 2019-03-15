package com.mabubu0203.sudoku.web.helper.bean;

import com.mabubu0203.sudoku.form.BaseForm;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.ui.Model;

/**
 * ControllerからHelperへ橋渡しするBeanです。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class HelperBean {

    private BaseForm form;
    private Model model;

}
