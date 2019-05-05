package com.mabubu0203.sudoku.utils;

import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

/**
 * Setを使用するUtil群です。<br>
 * .
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor(access = PRIVATE)
public class ESSetWrapUtils {

    /**
     * 数独の値で使用する連番を保持するSetを返却します。<br>
     * .
     *
     * @param size
     * @return Setを返却します
     */
    public static Set<Integer> getLinkedNum(int size) {
        Set<Integer> set = new LinkedHashSet<>();
        for (int i = 1; i < size + 1; i++) {
            set.add(i);
        }
        return set;
    }

}
