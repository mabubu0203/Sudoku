package com.mabubu0203.sudoku.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PageImplBean<T> extends PageImpl<T> {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Sort sort;

    public PageImplBean() {
        super(new ArrayList<T>());
    }

    public PageImplBean(final List<T> content) {
        super(content);
    }

    public PageImplBean(final List<T> content, final Pageable pageable, final long total) {
        super(content, pageable, total);
    }

}
