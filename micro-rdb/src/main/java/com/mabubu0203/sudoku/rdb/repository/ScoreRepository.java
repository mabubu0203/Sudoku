package com.mabubu0203.sudoku.rdb.repository;

import com.mabubu0203.sudoku.interfaces.domain.ScoreInfoTbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * {@code score_info_tbl}へのRepositoryクラスです。<br>
 * このクラスでCRUD操作を実装してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface ScoreRepository extends JpaRepository<ScoreInfoTbl, Long> {

    /**
     * {@code score_info_tbl}へ{@code type}と{@code keyHash}で検索を行います。<br>
     * 返却は0/1件を表すOptionalで返却します。<br>
     *
     * @param type
     * @param keyHash
     * @return 0/1件
     * @since 1.0
     */
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM score_info_tbl INNER JOIN answer_info_tbl ON score_info_tbl.no = answer_info_tbl.no WHERE answer_info_tbl.type = :type AND answer_info_tbl.keyhash = :keyHash"
    )
    Optional<ScoreInfoTbl> findByTypeAndKeyHash(@Param("type") Integer type, @Param("keyHash") String keyHash);

}
