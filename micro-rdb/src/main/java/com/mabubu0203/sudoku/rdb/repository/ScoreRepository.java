package com.mabubu0203.sudoku.rdb.repository;


import com.mabubu0203.sudoku.rdb.domain.ScoreInfoTbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * SCORE_INFO_TBLへのRepositoryクラスです。<br>
 * このクラスでCRUD操作を実装してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface ScoreRepository extends JpaRepository<ScoreInfoTbl, Long> {

    /**
     * SCORE_INFO_TBLへTypeとKeyHashで検索を行います。<br>
     *
     * @param type
     * @param keyHash
     * @return ScoreInfoTbl
     * @author uratamanabu
     * @since 1.0
     */
    @Query(
            value =
                    "SELECT * FROM SCORE_INFO_TBL INNER JOIN ANSWER_INFO_TBL ON SCORE_INFO_TBL.NO = ANSWER_INFO_TBL.NO WHERE ANSWER_INFO_TBL.TYPE = :TYPE AND ANSWER_INFO_TBL.KEYHASH = :KEYHASH",
            nativeQuery = true
    )
    ScoreInfoTbl findByTypeAndKeyHash(@Param("TYPE") Integer type, @Param("KEYHASH") String keyHash);

}
