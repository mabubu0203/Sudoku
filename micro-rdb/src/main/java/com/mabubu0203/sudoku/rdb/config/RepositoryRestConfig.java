package com.mabubu0203.sudoku.rdb.config;

import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.interfaces.domain.ScoreInfoTbl;
import com.mabubu0203.sudoku.interfaces.projection.SearchResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration(value = "com.mabubu0203.sudoku.rdb.config.RepositoryRestConfig")
public class RepositoryRestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(AnswerInfoTbl.class, ScoreInfoTbl.class);
        config.getProjectionConfiguration().addProjection(SearchResult.class);

    }

}