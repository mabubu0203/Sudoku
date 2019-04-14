package com.mabubu0203.sudoku.web.helper;

import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.utils.ESMapWrapUtils;
import com.mabubu0203.sudoku.web.form.CreateForm;
import com.mabubu0203.sudoku.web.helper.bean.HelperBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Objects;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Component
public class CreateHelper {

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
        URI uri = new UriTemplate(sudokuUriApi + "/createMaster/{type}").expand(form.getSelectType());
        RequestEntity requestEntity = RequestEntity.get(uri).build();
        try {
            ResponseEntity<String> generateEntity = restOperations.exchange(requestEntity, String.class);
            HttpStatus status = generateEntity.getStatusCode();
            switch (status) {
                case OK:
                    model.addAttribute("message", "登録完了です。");
                    model.addAttribute("keyHash", generateEntity.getBody());
                    break;
                case CONFLICT:
                default:
                    model.addAttribute("selectTypes", ESMapWrapUtils.getSelectTypes());
                    model.addAttribute("message", "新規追加失敗です。");
                    break;
            }
            return status;
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return HttpStatus.NO_CONTENT;
        }
    }

}
