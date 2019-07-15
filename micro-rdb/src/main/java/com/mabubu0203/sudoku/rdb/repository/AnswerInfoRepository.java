package com.mabubu0203.sudoku.rdb.repository;

import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * {@code answer_info_tbl}へのRepositoryクラスです。<br>
 * このクラスでCRUD操作を実装してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface AnswerInfoRepository
        extends JpaRepository<AnswerInfoTbl, Long>, JpaSpecificationExecutor<AnswerInfoTbl> {

    /**
     * {@code answer_info_tbl}へAnswerKeyで検索を行います。<br>
     * 返却はN件を表すStreamで返却します。<br>
     *
     * @param answerKey
     * @return N件
     * @since 1.0
     */
    Stream<AnswerInfoTbl> findByAnswerKey(@Param("answerkey") String answerKey);

    /**
     * {@code answer_info_tbl}へTypeとKeyHashで検索を行います。<br>
     * 返却は0/1件を表すOptionalで返却します。<br>
     *
     * @param type
     * @param keyHash
     * @return 0/1件
     * @since 1.0
     */
    Optional<AnswerInfoTbl> findByTypeAndKeyHash(@Param("type") Integer type, @Param("keyhash") String keyHash);

    /**
     * {@code answer_info_tbl}へTypeで検索を行います。<br>
     * 返却は0/1件を表すOptionalで返却します。<br>
     *
     * @param type
     * @return 0/1件
     * @since 1.0
     */
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM answer_info_tbl WHERE type = :type ORDER BY no DESC LIMIT 1"
    )
    Optional<AnswerInfoTbl> findFirstByType(@Param("type") Integer type);

}
