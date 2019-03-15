package com.mabubu0203.sudoku.interfaces.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mabubu0203.sudoku.interfaces.PageImplBean;
import com.mabubu0203.sudoku.interfaces.PagenationHelper;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Deprecated
public class SearchSudokuRecordResponseBean {

    private PagenationHelper ph;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = PageImplBean.class)
    private Page page;

}
