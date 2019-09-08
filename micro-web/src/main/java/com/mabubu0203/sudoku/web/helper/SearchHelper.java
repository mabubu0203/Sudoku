package com.mabubu0203.sudoku.web.helper;

import com.mabubu0203.sudoku.clients.api.RestApiSearchEndPoints;
import com.mabubu0203.sudoku.clients.rdb.custom.RdbApiSearchEndPoints;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.response.SearchResultBean;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

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
    private final RdbApiSearchEndPoints rdbApiSearchEndPoints;
    private final ModelMapper modelMapper;

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

        SearchConditionBean request = modelMapper.map(form, SearchConditionBean.class);

        Sort sort = Sort.by(Sort.Direction.DESC, "no");
        Pageable pageable = PageRequest.of(form.getPageNumber(), form.getPageSize(), sort);
        PagedResources<Resource<SearchResultBean>> page = rdbApiSearchEndPoints
                .search(restOperations, request, pageable);
        setPageInformation(model, page);
    }

    private void setPageInformation(Model model, PagedResources<Resource<SearchResultBean>> page) {

        List<SearchResultBean> content = page.getContent().stream().map(e -> e.getContent()).collect(toList());

        PagedResources.PageMetadata metadata = page.getMetadata();
        long pageSize = metadata.getSize();
        long totalElements = metadata.getTotalElements();
        long totalPages = metadata.getTotalPages();
        long pageNumber = metadata.getNumber();

        boolean existPrev = metadata.getNumber() != 0;
        boolean existNext = (totalPages != 0) && (pageNumber != totalPages - 1);

        model.addAttribute("content", content);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hiddenPrev", !existPrev);
        model.addAttribute("hiddenNext", !existNext);
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
            numberPlaceBean = sudokuModule.filteredOfLevel(form.getType(), numberPlaceBean, form.getSelectLevel());
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
