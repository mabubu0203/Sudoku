package com.mabubu0203.sudoku.web.logic;

import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.utils.ESMapWrapUtils;
import com.mabubu0203.sudoku.web.deprecated.LogicHandleBean;
import com.mabubu0203.sudoku.web.form.CreateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class CreateLogic {

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    public void createAnswer(final LogicHandleBean handleBean) {
        Model model = handleBean.getModel();
        if (model == null) {
            throw new SudokuApplicationException();
        } else {
            model.addAttribute("selectTypes", ESMapWrapUtils.getSelectTypes());
        }
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    public HttpStatus completeAnswer(
            final RestOperations restOperations, final LogicHandleBean handleBean) {

        CreateForm form = (CreateForm) handleBean.getForm();
        if (form == null) {
            throw new SudokuApplicationException();
        }
        Model model = handleBean.getModel();
        if (model == null) {
            throw new SudokuApplicationException();
        }
        URI uri =
                new UriTemplate("http://localhost:8085/SudokuApi/createMaster/{type}")
                        .expand(form.getSelectType());
        RequestEntity requestEntity = RequestEntity.get(uri).build();
        try {
            ResponseEntity<String> generateEntity = restOperations.exchange(requestEntity, String.class);
            if (generateEntity.getStatusCode() == HttpStatus.OK) {
                model.addAttribute("message", "登録完了です。");
                model.addAttribute("keyHash", generateEntity.getBody());
            } else if (generateEntity.getStatusCode() == HttpStatus.CONFLICT) {
                model.addAttribute("selectTypes", ESMapWrapUtils.getSelectTypes());
                model.addAttribute("message", "新規追加失敗です。");
            } else {
                model.addAttribute("selectTypes", ESMapWrapUtils.getSelectTypes());
                model.addAttribute("message", "新規追加失敗です。");
            }
            return generateEntity.getStatusCode();
        } catch (HttpClientErrorException e) {
            return HttpStatus.NO_CONTENT;
        }
    }

}
