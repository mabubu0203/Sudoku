package com.mabubu0203.sudoku.interfaces;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Deprecated
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageImplBean<T> extends PageImpl<T> {

    private static final long serialVersionUID = 1L;

    @JsonCreator(mode = Mode.PROPERTIES)
    public PageImplBean(@JsonProperty("content") final List<T> content,
                        @JsonProperty("number") final int number,
                        @JsonProperty("size") final int size,
                        @JsonProperty("totalElements") final long totalElements,
                        @JsonProperty("pageable") final JsonNode pageable,
                        @JsonProperty("last") final boolean last,
                        @JsonProperty("totalPages") final int totalPages,
                        @JsonProperty("sort") final JsonNode sort,
                        @JsonProperty("first") final boolean first,
                        @JsonProperty("numberOfElements") final int numberOfElements) {
        super(content, PageRequest.of(number, size), totalElements);
    }

    public PageImplBean() {
        super(new ArrayList<>());
    }

    public PageImplBean(final List<T> content) {
        super(content);
    }

    public PageImplBean(final List<T> content, final Pageable pageable, final long total) {
        super(content, pageable, total);
    }

}
