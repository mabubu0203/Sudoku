package com.mabubu0203.sudoku.web.helper;

import com.mabubu0203.sudoku.clients.api.RestApiSearchEndPoints;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.interfaces.request.SearchSudokuRecordRequestBean;
import com.mabubu0203.sudoku.interfaces.response.SearchSudokuRecordResponseBean;
import com.mabubu0203.sudoku.logic.SudokuModule;
import com.mabubu0203.sudoku.utils.ESListWrapUtils;
import com.mabubu0203.sudoku.utils.ESMapWrapUtils;
import com.mabubu0203.sudoku.utils.SudokuUtils;
import com.mabubu0203.sudoku.web.form.DetailForm;
import com.mabubu0203.sudoku.web.form.PlayForm;
import com.mabubu0203.sudoku.web.form.SearchForm;
import com.mabubu0203.sudoku.web.helper.bean.HelperBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.client.RestOperations;

import java.util.Objects;
import java.util.stream.Collectors;

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
public class SearchHelper {

    private final SudokuModule sudokuModule;
    private final RestApiSearchEndPoints restApiSearchEndPoints;

    @Value("${sudoku.uri.api}")
    private String sudokuUriApi;

    /**
     * <br>
     *
     * @param bean
     * @author uratamanabu
     * @since 1.0
     */
    public void searchAnswer(final HelperBean bean) {

        Model model = bean.getModel();
        if (Objects.isNull(model)) {
            throw new SudokuApplicationException();
        } else {
            model.addAttribute("selectTypes", ESMapWrapUtils.getSelectTypes());
            model.addAttribute("selectorNo", ESMapWrapUtils.getSelectorNo());
            model.addAttribute("selectorKeyHash", ESMapWrapUtils.getSelectorKeyHash());
            model.addAttribute("selectorScore", ESMapWrapUtils.getSelectorScore());
            model.addAttribute("selectorName", ESMapWrapUtils.getSelectorName());
        }
    }

    /**
     * <br>
     *
     * @param restOperations
     * @param bean
     * @author uratamanabu
     * @since 1.0
     */
    public void isSearch(final RestOperations restOperations, final HelperBean bean) {

        SearchForm form = (SearchForm) bean.getForm();
        Model model = bean.getModel();
        if (Objects.isNull(form) || Objects.isNull(model)) {
            throw new SudokuApplicationException();
        }
        model.addAttribute("selectTypes", ESMapWrapUtils.getSelectTypes());
        model.addAttribute("selectorNo", ESMapWrapUtils.getSelectorNo());
        model.addAttribute("selectorKeyHash", ESMapWrapUtils.getSelectorKeyHash());
        model.addAttribute("selectorScore", ESMapWrapUtils.getSelectorScore());
        model.addAttribute("selectorName", ESMapWrapUtils.getSelectorName());
        SearchSudokuRecordRequestBean request =
                new ModelMapper().map(form, SearchSudokuRecordRequestBean.class);

        PagedResources<Resource<AnswerInfoTbl>> pagedResources = restApiSearchEndPoints.search(restOperations, request);
        Page<Resource<AnswerInfoTbl>> page = new PageImpl(pagedResources.getContent().stream().collect(Collectors.toList()));
//            Page<SearchResultBean> page = generateEntity.getBody().getPage();
        // ページ番号を設定し直す
        form.setPageNumber(page.getNumber());
        model.addAttribute("page", page);
        model.addAttribute("ph", new SearchSudokuRecordResponseBean().getPh());
//            model.addAttribute("ph", generateEntity.getBody().getPh());

    }

    /**
     * <br>
     *
     * @param bean
     * @author uratamanabu
     * @since 1.0
     */
    public void detail(final HelperBean bean) {

        DetailForm form = (DetailForm) bean.getForm();
        Model model = bean.getModel();
        if (Objects.isNull(form) || Objects.isNull(model)) {
            throw new SudokuApplicationException();
        }
        model.addAttribute("detailForm", form);
        model.addAttribute("selectLevels", ESMapWrapUtils.getSelectLevels());
    }

    /**
     * <br>
     *
     * @param restOperations
     * @param bean
     * @author uratamanabu
     * @since 1.0
     */
    public void playNumberPlaceDetail(final RestOperations restOperations, final HelperBean bean) {

        DetailForm form = (DetailForm) bean.getForm();
        Model model = bean.getModel();
        if (Objects.isNull(form) || Objects.isNull(model)) {
            throw new SudokuApplicationException();
        }

        try {
            NumberPlaceBean numberPlaceBean = restApiSearchEndPoints
                    .sudoku(restOperations, form.getType(), form.getKeyHash());
            // 一件から虫食いのリストを取得します。
            numberPlaceBean =
                    sudokuModule.filteredOfLevel(form.getType(), numberPlaceBean, form.getSelectLevel());
            PlayForm playForm = new ModelMapper().map(numberPlaceBean, PlayForm.class);
            playForm.setCount(0);
            playForm.setScore(SudokuUtils.calculationScore(form.getType(), form.getSelectLevel()));
            model.addAttribute("playForm", playForm);
            model.addAttribute("selectNums", ESListWrapUtils.getSelectNum(form.getType()));
            model.addAttribute("selectCells", ESListWrapUtils.createCells(form.getType()));
        } catch (SudokuApplicationException e) {
            e.printStackTrace();
            throw new SudokuApplicationException();
        }

    }

}
