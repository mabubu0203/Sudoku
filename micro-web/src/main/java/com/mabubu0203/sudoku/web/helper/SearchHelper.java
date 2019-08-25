package com.mabubu0203.sudoku.web.helper;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.request.SearchSudokuRecordRequestBean;
import com.mabubu0203.sudoku.interfaces.response.SearchResultBean;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
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
@RequiredArgsConstructor
public class SearchHelper {

    private final SudokuModule sudokuModule;
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

        final String searchMaster = sudokuUriApi + RestUrlConstants.URL_SEARCH_MASTER + CommonConstants.SLASH;
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
        try {
            URI uri = new URI(searchMaster);
            RequestEntity requestEntity =
                    RequestEntity
                            .post(uri)
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .body(request);
            ResponseEntity<SearchSudokuRecordResponseBean> generateEntity =
                    restOperations.exchange(requestEntity, SearchSudokuRecordResponseBean.class);
            Page<SearchResultBean> page = generateEntity.getBody().getPage();
            // ページ番号を設定し直す
            form.setPageNumber(page.getNumber());
            model.addAttribute("page", page);
            model.addAttribute("ph", generateEntity.getBody().getPh());
        } catch (URISyntaxException | RestClientException e) {
            e.printStackTrace();
            throw new SudokuApplicationException();
        }
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

        final String searchMaster = sudokuUriApi + RestUrlConstants.URL_SEARCH_MASTER + CommonConstants.SLASH;
        DetailForm form = (DetailForm) bean.getForm();
        Model model = bean.getModel();
        if (Objects.isNull(form) || Objects.isNull(model)) {
            throw new SudokuApplicationException();
        }
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("type", Integer.toString(form.getType()));
        uriVariables.put("keyHash", form.getKeyHash());
        URI uri = new UriTemplate(searchMaster + "sudoku?type={type}&keyHash={keyHash}").expand(uriVariables);
        RequestEntity requestEntity =
                RequestEntity
                        .get(uri)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .build();
        try {
            ResponseEntity<NumberPlaceBean> generateEntity =
                    restOperations.exchange(requestEntity, NumberPlaceBean.class);
            NumberPlaceBean numberPlaceBean = generateEntity.getBody();
            // 一件から虫食いのリストを取得します。
            numberPlaceBean =
                    sudokuModule.filteredOfLevel(form.getType(), numberPlaceBean, form.getSelectLevel());
            PlayForm playForm = new ModelMapper().map(numberPlaceBean, PlayForm.class);
            playForm.setCount(0);
            playForm.setScore(SudokuUtils.calculationScore(form.getType(), form.getSelectLevel()));
            model.addAttribute("playForm", playForm);
            model.addAttribute("selectNums", ESListWrapUtils.getSelectNum(form.getType()));
            model.addAttribute("selectCells", ESListWrapUtils.createCells(form.getType()));
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new SudokuApplicationException();
        }
    }

}
