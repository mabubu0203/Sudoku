package com.mabubu0203.sudoku.rdb.service.impl;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.enums.Type;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.rdb.repository.AnswerInfoRepository;
import com.mabubu0203.sudoku.rdb.service.AnswerInfoService;
import com.mabubu0203.sudoku.rdb.specification.AnswerInfoSpecifications;
import com.mabubu0203.sudoku.rdb.specification.ScoreInfoSpecifications;
import com.mabubu0203.sudoku.utils.ESListWrapUtils;
import com.mabubu0203.sudoku.utils.NumberPlaceBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;

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
    private final ModelMapper modelMapper;

    @Override
    public AnswerInfoTbl insert(NumberPlaceBean numberplaceBean) {
        AnswerInfoTbl answerInfoTbl = modelMapper.map(numberplaceBean, AnswerInfoTbl.class);
        answerInfoTbl.setCreateDate(LocalDateTime.now());
        return answerInfoRepository.saveAndFlush(answerInfoTbl);
    }

    @Override
    public List<AnswerInfoTbl> searchByAnswerKey(String answerKey) {
        return answerInfoRepository.findByAnswerKey(answerKey);
    }

    @Override
    public Optional<AnswerInfoTbl> findFirstByType(Integer type) {
        return answerInfoRepository.findFirstByType(type);
    }

    @Override
    public Optional<AnswerInfoTbl> findByTypeAndKeyHash(Integer type, String keyHash) {
        return answerInfoRepository.findByTypeAndKeyHash(type, keyHash);
    }

    @Override
    public Page<AnswerInfoTbl> searchRecords(SearchConditionBean condition, Pageable pageable) {
        Specification<AnswerInfoTbl> answerSpecification = createSpecification(condition);
        Page<AnswerInfoTbl> page = answerInfoRepository.findAll(answerSpecification, pageable);
        return page;
    }

    @Override
    public NumberPlaceBean answerInfoTblConvertBean(AnswerInfoTbl answerInfoTbl) {
        NumberPlaceBean numberPlaceBean = new ModelMapper().map(answerInfoTbl, NumberPlaceBean.class);
        String answerKey = numberPlaceBean.getAnswerKey();
        String[] valueArray = answerKey.split("");
        Type type = Type.getType(numberPlaceBean.getType());
        if (type == null) {
            throw new SudokuApplicationException();
        }
        ListIterator<String> itr = ESListWrapUtils.createCells(type.getSize(), CommonConstants.INTEGER_ZERO).listIterator();
        try {
            for (String value : valueArray) {
                NumberPlaceBeanUtils.setCell(numberPlaceBean, itr.next(), Integer.valueOf(value));
            }
        } catch (SudokuApplicationException e) {
            e.printStackTrace();
            log.error("やらかしています。");
            throw new SudokuApplicationException();
        }
        return numberPlaceBean;
    }

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
