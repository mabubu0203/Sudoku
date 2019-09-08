package com.mabubu0203.sudoku.api.service.impl;

import com.mabubu0203.sudoku.api.service.SearchService;
import com.mabubu0203.sudoku.clients.rdb.domains.AnswerInfoTblEndPoints;
import com.mabubu0203.sudoku.clients.rdb.domains.ScoreInfoTblEndPoints;
import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.enums.Type;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.interfaces.domain.ScoreInfoTbl;
import com.mabubu0203.sudoku.interfaces.response.ScoreResponseBean;
import com.mabubu0203.sudoku.interfaces.response.SearchResultBean;
import com.mabubu0203.sudoku.utils.ESListWrapUtils;
import com.mabubu0203.sudoku.utils.NumberPlaceBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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
            final int type,
            final String keyHash
    ) {

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
            final int type,
            final String keyHash
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

    private Page<SearchResultBean> convertJacksonFile(final PagedResources<Resource<SearchResultBean>> page) {

        log.info(page.toString());
        Pageable pageable = PageRequest.of((int) page.getMetadata().getNumber(), (int) page.getMetadata().getSize());
        List<SearchResultBean> content = page.getContent().stream().map(e -> e.getContent()).collect(toList());
        List<SearchResultBean> modifyContent = new ArrayList<>();
        for (SearchResultBean record : content) {
            SearchResultBean bean = new SearchResultBean();
            bean.setNo(record.getNo());
            bean.setType(record.getType());
            bean.setKeyHash(record.getKeyHash());
            bean.setName(record.getName());
            bean.setScore(record.getScore());
            bean.setMemo(record.getMemo());
            bean.setUpdateDate(record.getUpdateDate());
            modifyContent.add(bean);
        }
        Page<SearchResultBean> result = new PageImpl(modifyContent, pageable, page.getMetadata().getTotalElements());
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
