package com.mabubu0203.sudoku.rdb.specification;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.enums.Selector;
import com.mabubu0203.sudoku.rdb.domain.AnswerInfoTbl;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

/**
 * ANSWER_INFO_TBLへの検索条件をSpecificationで返却するクラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class AnswerInfoSpecifications {

    private static final String NO = "no";
    private static final String TYPE = "type";
    private static final String KEYHASH = "keyHash";

    /**
     * <br>
     *
     * @param selectType
     * @return Specification
     * @since 1.0
     */
    public static Specification<AnswerInfoTbl> typeContains(Integer selectType) {
        return (root, query, builder) -> builder.equal(root.get(TYPE), selectType);
    }

    /**
     * <br>
     *
     * @param no
     * @param selectorNo
     * @return Specification
     * @since 1.0
     */
    public static Specification<AnswerInfoTbl> noContains(Long no, Integer selectorNo) {
        if (CommonConstants.LONG_ZERO.equals(no)) {
            return null;
        } else {
            Selector selector = Selector.getSelector(selectorNo);
            if (selector == null) {
                return (root, query, builder) -> builder.equal(root.get(NO), no);
            }
            switch (selector) {
                case PERFECT_MATCH:
                    return (root, query, builder) -> builder.equal(root.get(NO), no);
                case AROUND5:
                    long fromNo = no - 5 < 0 ? 0 : no - 5;
                    long endNo = no + 5 > 9999999999L ? 9999999999L : no + 5;
                    return (root, query, builder) -> builder.between(root.get(NO), fromNo, endNo);
                case MORE_BIG:
                    return (root, query, builder) -> builder.greaterThan(root.get(NO), no);
                case MORE_SMALL:
                    return (root, query, builder) -> builder.lessThan(root.get(NO), no);
                default:
                    return (root, query, builder) -> builder.equal(root.get(NO), no);
            }
        }
    }

    /**
     * <br>
     *
     * @param keyHash
     * @param selectorKeyHash
     * @return Specification
     * @since 1.0
     */
    public static Specification<AnswerInfoTbl> keyHashContains(String keyHash, int selectorKeyHash) {
        if (Objects.isNull(keyHash)) {
            return null;
        } else {
            Selector selector = Selector.getSelector(selectorKeyHash);
            if (selector == null) {
                return (root, query, builder) -> builder.equal(root.get(KEYHASH), keyHash);
            }
            switch (selector) {
                case PERFECT_MATCH:
                    return (root, query, builder) -> builder.equal(root.get(KEYHASH), keyHash);
                case FORWARD_MATCH:
                    return (root, query, builder) -> builder.like(root.get(KEYHASH), keyHash + '%');
                case BACKWARD_MATCH:
                    return (root, query, builder) -> builder.like(root.get(KEYHASH), '%' + keyHash);
                case PARTIAL_MATCH:
                    return (root, query, builder) -> builder.like(root.get(KEYHASH), '%' + keyHash + '%');
                default:
                    return (root, query, builder) -> builder.equal(root.get(KEYHASH), keyHash);
            }
        }
    }
}
