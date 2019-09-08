package com.mabubu0203.sudoku.rdb.service.impl;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.interfaces.projection.SearchResult;
import com.mabubu0203.sudoku.rdb.repository.AnswerInfoRepository;
import com.mabubu0203.sudoku.rdb.service.AnswerInfoService;
import com.mabubu0203.sudoku.rdb.specification.AnswerInfoSpecifications;
import com.mabubu0203.sudoku.rdb.specification.ScoreInfoSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * ANSWER_INFO_TBLへのサービスクラスです。 <br>
 * このクラスを経由してCRUD操作を実行してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AnswerInfoServiceImpl implements AnswerInfoService {

    private final AnswerInfoRepository answerInfoRepository;
    private final PagedResourcesAssembler assembler;
    private final ProjectionFactory factory;
    private final ModelMapper modelMapper;

    @Override
    public AnswerInfoTbl insert(NumberPlaceBean numberplaceBean) {
        AnswerInfoTbl answerInfoTbl = modelMapper.map(numberplaceBean, AnswerInfoTbl.class);
        answerInfoTbl.setCreateDate(LocalDateTime.now());
        return answerInfoRepository.saveAndFlush(answerInfoTbl);
    }

    @Override
    public PagedResources<Resource<SearchResult>> searchRecords(SearchConditionBean condition, Pageable pageable) {
        Specification<AnswerInfoTbl> answerSpecification = createSpecification(condition);
        Page<AnswerInfoTbl> page = answerInfoRepository.findAll(answerSpecification, pageable);
        Page<SearchResult> projected = page.map(l -> factory.createProjection(SearchResult.class, l));
        PagedResources<Resource<SearchResult>> pagedResources = assembler.toResource(projected);
        return pagedResources;
    }

    @Deprecated
    private Specification<AnswerInfoTbl> createSpecification(SearchConditionBean condition) {
        Specification<AnswerInfoTbl> specification = Specification.where(AnswerInfoSpecifications.typeContains(condition.getType()));

        Specification<AnswerInfoTbl> noContains =
                AnswerInfoSpecifications.noContains(condition.getNo(), condition.getSelectorNo());
        if (Objects.nonNull(noContains)) {
            specification.and(noContains);
        }

        Specification<AnswerInfoTbl> keyHashContains =
                AnswerInfoSpecifications.keyHashContains(condition.getKeyHash(), condition.getSelectorKeyHash());
        if (Objects.nonNull(keyHashContains)) {
            specification.and(keyHashContains);
        }

        Specification<AnswerInfoTbl> scoreContains =
                ScoreInfoSpecifications.scoreContains(condition.getScore(), condition.getSelectorScore());
        if (Objects.nonNull(scoreContains)) {
            specification.and(scoreContains);
        }

        Specification<AnswerInfoTbl> nameContains =
                ScoreInfoSpecifications.nameContains(condition.getName(), condition.getSelectorName());
        if (Objects.nonNull(nameContains)) {
            specification.and(nameContains);
        }

        Specification<AnswerInfoTbl> dateContains =
                ScoreInfoSpecifications.dateContains(condition.getDateStart(), condition.getDateEnd());
        if (Objects.nonNull(dateContains)) {
            specification.and(dateContains);
        }

        return specification;
    }

}
