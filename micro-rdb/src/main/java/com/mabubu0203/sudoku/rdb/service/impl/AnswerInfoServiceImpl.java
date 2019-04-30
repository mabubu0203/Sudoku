package com.mabubu0203.sudoku.rdb.service.impl;

import com.mabubu0203.sudoku.enums.Type;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.rdb.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.rdb.repository.AnswerInfoRepository;
import com.mabubu0203.sudoku.rdb.service.AnswerInfoService;
import com.mabubu0203.sudoku.rdb.specification.AnswerInfoSpecifications;
import com.mabubu0203.sudoku.rdb.specification.ScoreInfoSpecifications;
import com.mabubu0203.sudoku.utils.ESListWrapUtils;
import com.mabubu0203.sudoku.utils.SudokuUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

/**
 * ANSWER_INFO_TBLへのサービスクラスです。 <br>
 * このクラスを経由してCRUD操作を実行してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@AllArgsConstructor
@Service
public class AnswerInfoServiceImpl implements AnswerInfoService {

    private static final String NO = "no";

    private final AnswerInfoRepository answerInfoRepository;
    private final ModelMapper modelMapper;

    @Override
    public AnswerInfoTbl insert(NumberPlaceBean numberplaceBean) {
        AnswerInfoTbl answerInfoTbl = modelMapper.map(numberplaceBean, AnswerInfoTbl.class);
        answerInfoTbl.setCreateDate(LocalDateTime.now());
        return answerInfoRepository.saveAndFlush(answerInfoTbl);
    }

    @Override
    public Stream<AnswerInfoTbl> select(NumberPlaceBean numberplaceBean) {
        return answerInfoRepository.findByAnswerKey(numberplaceBean.getAnswerKey());
    }

    @Override
    public Stream<AnswerInfoTbl> findByAnswerKey(String answerKey) {
        return answerInfoRepository.findByAnswerKey(answerKey);
    }

    @Override
    public AnswerInfoTbl findByType(Integer type) {
        return answerInfoRepository.findByType(type);
    }

    @Override
    public AnswerInfoTbl findByTypeAndKeyHash(Integer type, String keyHash) {
        return answerInfoRepository.findByTypeAndKeyHash(type, keyHash);
    }

    @Override
    public Page<AnswerInfoTbl> findRecords(SearchConditionBean condition, Pageable pageable) {

        Specification<AnswerInfoTbl> typeContains =
                AnswerInfoSpecifications.typeContains(condition.getType());
        Specification<AnswerInfoTbl> noContains =
                AnswerInfoSpecifications.noContains(condition.getNo(), condition.getSelectorNo());
        Specification<AnswerInfoTbl> keyHashContains =
                AnswerInfoSpecifications.keyHashContains(
                        condition.getKeyHash(), condition.getSelectorKeyHash());
        Specification<AnswerInfoTbl> scoreContains =
                ScoreInfoSpecifications.scoreContains(condition.getScore(), condition.getSelectorScore());
        Specification<AnswerInfoTbl> nameContains =
                ScoreInfoSpecifications.nameContains(condition.getName(), condition.getSelectorName());
        Specification<AnswerInfoTbl> dateContains =
                ScoreInfoSpecifications.dateContains(condition.getDateStart(), condition.getDateEnd());

        Specifications<AnswerInfoTbl> answerSpecification = Specifications.where(typeContains);
        if (noContains != null) {
            answerSpecification.and(noContains);
        }
        if (keyHashContains != null) {
            answerSpecification.and(keyHashContains);
        }
        if (scoreContains != null) {
            answerSpecification.and(scoreContains);
        }
        if (nameContains != null) {
            answerSpecification.and(nameContains);
        }
        if (dateContains != null) {
            answerSpecification.and(dateContains);
        }
        Page<AnswerInfoTbl> page = answerInfoRepository.findAll(answerSpecification, pageable);
        return page;
    }

    @Override
    public NumberPlaceBean answerInfoTblConvertBean(AnswerInfoTbl answerInfoTbl) {
        NumberPlaceBean numberPlaceBean = new ModelMapper().map(answerInfoTbl, NumberPlaceBean.class);
        String answerKey = numberPlaceBean.getAnswerKey();
        String[] valueArray = answerKey.split("");
        int size = 0;
        Type type = Type.getType(numberPlaceBean.getType());
        if (type == null) {
            throw new SudokuApplicationException();
        } else {
            size = type.getSize();
        }
        ListIterator<String> itr = ESListWrapUtils.createCells(size, 0).listIterator();
        try {
            for (String value : valueArray) {
                SudokuUtils.setCell(numberPlaceBean, itr.next(), Integer.valueOf(value));
            }
        } catch (SudokuApplicationException e) {
            e.printStackTrace();
            log.error("やらかしています。");
            throw new SudokuApplicationException();
        }
        return numberPlaceBean;
    }


}
