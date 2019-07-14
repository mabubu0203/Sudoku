package com.mabubu0203.sudoku.api.service.impl;

import com.mabubu0203.sudoku.api.service.SearchService;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.PagenationHelper;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.response.ScoreResponseBean;
import com.mabubu0203.sudoku.interfaces.response.SearchResultBean;
import com.mabubu0203.sudoku.interfaces.response.SearchSudokuRecordResponseBean;
import com.mabubu0203.sudoku.rdb.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.rdb.domain.ScoreInfoTbl;
import com.mabubu0203.sudoku.rdb.service.AnswerInfoService;
import com.mabubu0203.sudoku.rdb.service.ScoreInfoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 検索する為のサービスクラスです。<br>
 * このクラスを経由してロジックを実行してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private AnswerInfoService answerInfoService;
    @Autowired
    private ScoreInfoService scoreInfoService;
    @Autowired
    @Qualifier("com.mabubu0203.sudoku.api.config.ModelMapperConfiguration.ModelMapper")
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<Boolean> isExist(final String answerKey) {

        // TODO:answerInfoRepository.findByAnswerKey(answerKey)
        try (Stream<AnswerInfoTbl> stream = answerInfoService.searchByAnswerKey(answerKey)) {
            if (stream.count() == 0) {
                return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<SearchSudokuRecordResponseBean> search(
            final SearchConditionBean conditionBean,
            final int pageNumber,
            final int pageSize) {

        Sort sort = Sort.by(Sort.Direction.DESC, "no");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        // TODO:answerInfoRepository.findAll(answerSpecification, pageable)
        Page<AnswerInfoTbl> page = answerInfoService.searchRecords(conditionBean, pageable);
        if (Objects.nonNull(page) && page.hasContent()) {
            Page<SearchResultBean> modiftyPage = convertJacksonFile(page);
            SearchSudokuRecordResponseBean response = new SearchSudokuRecordResponseBean();
            response.setPage(modiftyPage);
            response.setPh(new PagenationHelper(modiftyPage));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            SearchSudokuRecordResponseBean response = new SearchSudokuRecordResponseBean();
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(final int type) {

        // TODO:answerInfoRepository.findFirstByType(type)
        Optional<AnswerInfoTbl> answerInfoTblOpt = answerInfoService.findFirstByType(type);
        if (answerInfoTblOpt.isPresent()) {
            NumberPlaceBean numberPlaceBean = answerInfoService.answerInfoTblConvertBean(answerInfoTblOpt.get());
            return new ResponseEntity<>(numberPlaceBean, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(final int type, final String keyHash) {

        // TODO:answerInfoRepository.findByTypeAndKeyHash(type, keyHash)
        Optional<AnswerInfoTbl> answerInfoTblOpt = answerInfoService.findByTypeAndKeyHash(type, keyHash);
        if (answerInfoTblOpt.isPresent()) {
            NumberPlaceBean numberPlaceBean = answerInfoService.answerInfoTblConvertBean(answerInfoTblOpt.get());
            return new ResponseEntity<>(numberPlaceBean, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<ScoreResponseBean> getScore(final int type, final String keyHash) {

        // TODO:scoreRepository.findByTypeAndKeyHash(type, keyHash)
        Optional<ScoreInfoTbl> scoreInfoTblOpt = scoreInfoService.findByTypeAndKeyHash(type, keyHash);
        if (scoreInfoTblOpt.isPresent()) {
            ScoreResponseBean response = modelMapper.map(scoreInfoTblOpt.get(), ScoreResponseBean.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    private Page<SearchResultBean> convertJacksonFile(final Page<AnswerInfoTbl> page) {

        Pageable pageable = PageRequest.of(page.getNumber(), page.getSize());
        List<AnswerInfoTbl> content = page.getContent();
        List<SearchResultBean> modifyContent = new ArrayList<>();
        for (AnswerInfoTbl record : content) {
            SearchResultBean bean = new SearchResultBean();
            bean.setNo(record.getNo());
            bean.setType(record.getType());
            bean.setKeyHash(record.getKeyHash());
            ScoreInfoTbl scoreInfoTbl = record.getScoreInfoTbl();
            bean.setName(scoreInfoTbl.getName());
            bean.setScore(scoreInfoTbl.getScore());
            bean.setMemo(scoreInfoTbl.getMemo());
            bean.setUpdateDate(scoreInfoTbl.getUpdateDate());
            modifyContent.add(bean);
        }
        Page<SearchResultBean> result = new PageImpl(modifyContent, pageable, page.getTotalElements());
        return result;
    }

}
