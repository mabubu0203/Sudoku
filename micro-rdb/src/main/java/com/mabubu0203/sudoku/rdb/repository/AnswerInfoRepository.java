package com.mabubu0203.sudoku.rdb.repository;


import com.mabubu0203.sudoku.rdb.domain.AnswerInfoTbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * ANSWER_INFO_TBLへのRepositoryクラスです。<br>
 * このクラスでCRUD操作を実装してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface AnswerInfoRepository
        extends JpaRepository<AnswerInfoTbl, Long>, JpaSpecificationExecutor<AnswerInfoTbl> {

    /**
     * ANSWER_INFO_TBLへAnswerKeyで検索を行います。<br>
     *
     * @param answerKey
     * @return List
     * @author uratamanabu
     * @since 1.0
     */
    List<AnswerInfoTbl> findByAnswerKey(String answerKey);

    /**
     * ANSWER_INFO_TBLへTypeとKeyHashで検索を行います。<br>
     *
     * @param type
     * @param keyHash
     * @return AnswerInfoTbl
     * @author uratamanabu
     * @since 1.0
     */
    AnswerInfoTbl findByTypeAndKeyHash(Integer type, String keyHash);

    /**
     * ANSWER_INFO_TBLへTypeで検索を行います。<br>
     *
     * @param type
     * @return AnswerInfoTbl
     * @author uratamanabu
     * @since 1.0
     */
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM ANSWER_INFO_TBL WHERE TYPE = :TYPE ORDER BY NO DESC LIMIT 1"
    )
    AnswerInfoTbl findByType(@Param("TYPE") Integer type);

}
