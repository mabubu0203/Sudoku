package com.mabubu0203.sudoku.rdb.repository;


import com.mabubu0203.sudoku.rdb.domain.AnswerInfoTbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.stream.Stream;

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
     * 返却はN件を表すStreamで返却します。<br>
     *
     * @param answerKey
     * @return N件
     * @author uratamanabu
     * @since 1.0
     */
    Stream<AnswerInfoTbl> findByAnswerKey(String answerKey);

    /**
     * ANSWER_INFO_TBLへTypeとKeyHashで検索を行います。<br>
     * 返却は0/1件を表すOptionalで返却します。<br>
     *
     * @param type
     * @param keyHash
     * @return 0/1件
     * @author uratamanabu
     * @since 1.0
     */
    Optional<AnswerInfoTbl> findByTypeAndKeyHash(Integer type, String keyHash);

    /**
     * ANSWER_INFO_TBLへTypeで検索を行います。<br>
     * 返却は0/1件を表すOptionalで返却します。<br>
     *
     * @param type
     * @return 0/1件
     * @author uratamanabu
     * @since 1.0
     */
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM ANSWER_INFO_TBL WHERE TYPE = :TYPE ORDER BY NO DESC LIMIT 1"
    )
    Optional<AnswerInfoTbl> findFirstByType(@Param("TYPE") Integer type);

}
