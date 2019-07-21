package com.mabubu0203.sudoku.api.service.impl;

import com.mabubu0203.sudoku.api.service.SearchService;
import com.mabubu0203.sudoku.clients.rdb.AnswerInfoTblEndPoints;
import com.mabubu0203.sudoku.clients.rdb.RdbApiSearchEndPoints;
import com.mabubu0203.sudoku.clients.rdb.ScoreInfoTblEndPoints;
import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.enums.Type;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.PagenationHelper;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.interfaces.domain.ScoreInfoTbl;
import com.mabubu0203.sudoku.interfaces.response.ScoreResponseBean;
import com.mabubu0203.sudoku.interfaces.response.SearchResultBean;
import com.mabubu0203.sudoku.interfaces.response.SearchSudokuRecordResponseBean;
import com.mabubu0203.sudoku.utils.ESListWrapUtils;
import com.mabubu0203.sudoku.utils.NumberPlaceBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.*;

/**
 * 検索する為のサービスクラスです。<br>
 * このクラスを経由してロジックを実行してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {


    private final RdbApiSearchEndPoints rdbApiSearchEndPoints;
    private final AnswerInfoTblEndPoints answerInfoTblEndpoints;
    private final ScoreInfoTblEndPoints scoreInfoTblEndPoints;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<Boolean> isExist(final RestOperations restOperations, final String answerKey) {

        List<AnswerInfoTbl> list = answerInfoTblEndpoints.findByAnswerKey(restOperations, answerKey);
        if (list.size() == 0) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<SearchSudokuRecordResponseBean> search(
            final RestOperations restOperations,
            final SearchConditionBean conditionBean,
            final int pageNumber,
            final int pageSize
    ) {

        Sort sort = Sort.by(Sort.Direction.DESC, "no");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<AnswerInfoTbl> page = rdbApiSearchEndPoints.search(restOperations, conditionBean, pageable);
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
    public ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(final RestOperations restOperations, final int type) {

        Optional<AnswerInfoTbl> answerInfoTblOpt = answerInfoTblEndpoints.findFirstByType(restOperations, type);
        if (answerInfoTblOpt.isPresent()) {
            NumberPlaceBean numberPlaceBean = answerInfoTblConvertBean(answerInfoTblOpt.get());
            return new ResponseEntity<>(numberPlaceBean, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(
            final RestOperations restOperations,
            final int type, final String keyHash) {

        Optional<AnswerInfoTbl> answerInfoTblOpt = answerInfoTblEndpoints.findByTypeAndKeyHash(
                restOperations, type, keyHash);
        if (answerInfoTblOpt.isPresent()) {
            NumberPlaceBean numberPlaceBean = answerInfoTblConvertBean(answerInfoTblOpt.get());
            return new ResponseEntity<>(numberPlaceBean, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<ScoreResponseBean> getScore(
            final RestOperations restOperations,
            final int type, final String keyHash
    ) {

        Optional<ScoreInfoTbl> scoreInfoTblOpt =
                scoreInfoTblEndPoints.findByTypeAndKeyHash(restOperations, type, keyHash);

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

    private NumberPlaceBean answerInfoTblConvertBean(AnswerInfoTbl answerInfoTbl) {
        NumberPlaceBean numberPlaceBean = new ModelMapper().map(answerInfoTbl, NumberPlaceBean.class);
        String answerKey = numberPlaceBean.getAnswerKey();
        String[] valueArray = answerKey.split(CommonConstants.EMPTY_STR);
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

}
