package com.mabubu0203.sudoku.rdb.specification;

import com.mabubu0203.sudoku.enums.Selector;
import com.mabubu0203.sudoku.rdb.domain.AnswerInfoTbl;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import java.time.LocalDate;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

/**
 * SCORE_INFO_TBLへの検索条件をSpecificationで返却するクラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class ScoreInfoSpecifications {

    private static final String SCORE = "score";
    private static final String NAME = "name";
    private static final String UPDATEDATE = "updateDate";
    private static final String SCOREINFOTBL = "scoreInfoTbl";

    /**
     * <br>
     *
     * @param score
     * @param selectorScore
     * @return Specification
     * @author uratamanabu
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public static Specification<AnswerInfoTbl> scoreContains(int score, int selectorScore) {
        if (score == 0) {
            return null;
        } else {
            Selector selector = Selector.getSelector(selectorScore);
            if (selector == null) {
                return (root, query, builder) ->
                        builder.equal(root.join(SCOREINFOTBL, JoinType.INNER).get(SCORE), score);
            }
            switch (selector) {
                case PERFECT_MATCH:
                    return (root, query, builder) ->
                            builder.equal(root.join(SCOREINFOTBL, JoinType.INNER).get(SCORE), score);
                case MORE_BIG:
                    return (root, query, builder) ->
                            builder.greaterThan(root.join(SCOREINFOTBL, JoinType.INNER).get(SCORE), score);
                case MORE_SMALL:
                    return (root, query, builder) ->
                            builder.lessThan(root.join(SCOREINFOTBL, JoinType.INNER).get(SCORE), score);
                default:
                    return (root, query, builder) ->
                            builder.equal(root.join(SCOREINFOTBL, JoinType.INNER).get(SCORE), score);
            }
        }
    }

    /**
     * <br>
     *
     * @param name
     * @param selectorName
     * @return Specification
     * @author uratamanabu
     * @since 1.0
     */
    public static Specification<AnswerInfoTbl> nameContains(String name, int selectorName) {
        if (name == null) {
            return null;
        }
        final String field = name;
        Selector selector = Selector.getSelector(selectorName);
        if (selector == null) {
            return (root, query, builder) ->
                    builder.equal(root.join(SCOREINFOTBL, JoinType.INNER).get(NAME), field);
        }

        switch (selector) {
            case PERFECT_MATCH:
                return (root, query, builder) ->
                        builder.equal(root.join(SCOREINFOTBL, JoinType.INNER).get(NAME), field);
            case FORWARD_MATCH:
                return (root, query, builder) ->
                        builder.like(root.join(SCOREINFOTBL, JoinType.INNER).get(NAME), field + '%');
            case BACKWARD_MATCH:
                return (root, query, builder) ->
                        builder.like(root.join(SCOREINFOTBL, JoinType.INNER).get(NAME), '%' + field);
            case PARTIAL_MATCH:
                return (root, query, builder) ->
                        builder.like(root.join(SCOREINFOTBL, JoinType.INNER).get(NAME), '%' + field + '%');
            default:
                return (root, query, builder) ->
                        builder.equal(root.join(SCOREINFOTBL, JoinType.INNER).get(NAME), field);
        }
    }

    /**
     * <br>
     *
     * @param dateStart
     * @param dateEnd
     * @return Specification
     * @author uratamanabu
     * @since 1.0
     */
    public static Specification<AnswerInfoTbl> dateContains(LocalDate dateStart, LocalDate dateEnd) {
        Optional<LocalDate> dateStartOpt = Optional.ofNullable(dateStart);
        Optional<LocalDate> dateEndOpt = Optional.ofNullable(dateEnd);
        if (dateStartOpt.isPresent() && dateEndOpt.isPresent()) {
            return (root, query, builder) ->
                    builder.between(
                            root.join(SCOREINFOTBL, JoinType.INNER).get(UPDATEDATE),
                            dateStart.atStartOfDay(),
                            dateEnd.plusDays(1).atStartOfDay());
        } else if (dateStartOpt.isPresent() && !dateEndOpt.isPresent()) {
            return (root, query, builder) ->
                    builder.greaterThan(
                            root.join(SCOREINFOTBL, JoinType.INNER).get(UPDATEDATE), dateStart.atStartOfDay());
        } else if (!dateStartOpt.isPresent() && dateEndOpt.isPresent()) {
            return (root, query, builder) ->
                    builder.lessThan(
                            root.join(SCOREINFOTBL, JoinType.INNER).get(UPDATEDATE), dateStart.atStartOfDay());
        } else if (!dateStartOpt.isPresent() && !dateEndOpt.isPresent()) {
            return null;
        } else {
            return null;
        }
    }

}
