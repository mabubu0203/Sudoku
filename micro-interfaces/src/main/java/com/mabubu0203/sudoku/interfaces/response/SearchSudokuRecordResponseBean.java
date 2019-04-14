package com.mabubu0203.sudoku.interfaces.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.mabubu0203.sudoku.interfaces.PageImplBean;
import com.mabubu0203.sudoku.interfaces.PagenationHelper;
import lombok.Data;
import org.springframework.data.domain.Page;


@Data
@Deprecated
public class SearchSudokuRecordResponseBean {

    private PagenationHelper ph;

    @JsonTypeInfo(use = Id.NAME, defaultImpl = PageImplBean.class)
    private Page<SearchResultBean> page;

}
