package com.mabubu0203.sudoku.rdb.service.impl;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.rdb.domain.ScoreInfoTbl;
import com.mabubu0203.sudoku.rdb.repository.ScoreRepository;
import com.mabubu0203.sudoku.rdb.service.ScoreInfoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * SCORE_INFO_TBLへのサービスクラスです。 このクラスを経由してCRUD操作を実行してください。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
public class ScoreInfoServiceImpl implements ScoreInfoService {

    @SuppressWarnings("initialization.fields.uninitialized")
    @Autowired
    private ScoreRepository scoreRepository;

    @SuppressWarnings("initialization.fields.uninitialized")
    @Autowired
    private ModelMapper modelMapper;

    /**
     * SCORE_INFO_TBLへ空スコア情報をインサートします。
     *
     * @param numberplaceBean 数独
     * @return ScoreInfoTbl
     * @author uratamanabu
     * @since 1.0
     */
    public ScoreInfoTbl insert(NumberPlaceBean numberplaceBean) {
        ScoreInfoTbl scoreInfoTbl = modelMapper.map(numberplaceBean, ScoreInfoTbl.class);
        scoreInfoTbl.setScore(CommonConstants.ZERO);
        scoreInfoTbl.setName(CommonConstants.EMPTY_STR);
        scoreInfoTbl.setMemo(CommonConstants.EMPTY_STR);
        scoreInfoTbl.setUpdateDate(LocalDateTime.now());
        return scoreRepository.save(scoreInfoTbl);
    }

    /**
     * SCORE_INFO_TBLへTypeとKeyHashで検索を行います。
     *
     * @param type    タイプ
     * @param keyHash KeyHash
     * @return ScoreInfoTbl
     * @author uratamanabu
     * @since 1.0
     */
    public ScoreInfoTbl findByTypeAndKeyHash(int type, String keyHash) {
        return scoreRepository.findByTypeAndKeyHash(type, keyHash);
    }

    /**
     * SCORE_INFO_TBLへスコア情報をアップデートします。
     *
     * @param numberplaceBean 数独
     * @return ScoreInfoTbl
     * @author uratamanabu
     * @since 1.0
     */
    public ScoreInfoTbl update(NumberPlaceBean numberplaceBean) {
        ScoreInfoTbl scoreInfoTbl = modelMapper.map(numberplaceBean, ScoreInfoTbl.class);
        scoreInfoTbl.setUpdateDate(LocalDateTime.now());
        return scoreRepository.save(scoreInfoTbl);
    }

    /**
     * SCORE_INFO_TBLへスコア情報をアップデートします。
     *
     * @param scoreInfoTbl スコア情報
     * @return ScoreInfoTbl
     * @author uratamanabu
     * @since 1.0
     */
    public ScoreInfoTbl update(ScoreInfoTbl scoreInfoTbl) {
        scoreInfoTbl.setUpdateDate(LocalDateTime.now());
        return scoreRepository.save(scoreInfoTbl);
    }

}
