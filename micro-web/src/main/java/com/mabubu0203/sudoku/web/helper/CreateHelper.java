package com.mabubu0203.sudoku.web.helper;

import com.mabubu0203.sudoku.clients.api.RestApiCreateEndPoints;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.utils.ESMapWrapUtils;
import com.mabubu0203.sudoku.web.form.CreateForm;
import com.mabubu0203.sudoku.web.helper.bean.HelperBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.client.RestOperations;

import java.util.Objects;
import java.util.Optional;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CreateHelper {

    private final RestApiCreateEndPoints restApiCreateEndPoints;

    @Value("${sudoku.uri.api}")
    private String sudokuUriApi;

    /**
     * <br>
     *
     * @param bean
     * @author uratamanabu
     * @since 1.0
     */
    public void createAnswer(final HelperBean bean) {
        Model model = bean.getModel();
        if (Objects.isNull(model)) {
            throw new SudokuApplicationException();
        } else {
            model.addAttribute("selectTypes", ESMapWrapUtils.getSelectTypes());
        }
    }

    /**
     * <br>
     *
     * @param restOperations
     * @param bean
     * @return HttpStatus
     * @author uratamanabu
     * @since 1.0
     */
    public HttpStatus completeAnswer(final RestOperations restOperations, final HelperBean bean) {

        CreateForm form = (CreateForm) bean.getForm();
        Model model = bean.getModel();
        if (Objects.isNull(form) || Objects.isNull(model)) {
            throw new SudokuApplicationException();
        }

        Optional<String> keyHashOpt = restApiCreateEndPoints.createMaster(restOperations, form.getSelectType());

        if (keyHashOpt.isPresent()) {
            model.addAttribute("message", "登録完了です。");
            model.addAttribute("keyHash", keyHashOpt.get());
            return HttpStatus.OK;
        } else {
            model.addAttribute("selectTypes", ESMapWrapUtils.getSelectTypes());
            model.addAttribute("message", "新規追加失敗です。");
            return HttpStatus.NO_CONTENT;
        }
    }

}
