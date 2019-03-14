package com.mabubu0203.sudoku.enums;

import lombok.Getter;

/**
 * 数独の難易度を列挙型で定義します。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public enum Difficulty {
    /**
     * size:4プレイ時の難易度:低の時の虫食い数を示します
     */
    EASY4(1),
    /**
     * size:9プレイ時の難易度:低の時の虫食い数を示します
     */
    EASY9(2),
    /**
     * size:4プレイ時の難易度:中の時の虫食い数を示します
     */
    NORMAL4(2),
    /**
     * size:9プレイ時の難易度:中の時の虫食い数を示します
     */
    NORMAL9(4),
    /**
     * size:3プレイ時の難易度:強の時の虫食い数を示します
     */
    HARD4(3),
    /**
     * size:9プレイ時の難易度:強の時の虫食い数を示します
     */
    HARD9(6);

    /**
     * 虫食い数を定義します。
     */
    @Getter
    private int value;

    /**
     * コンストラクタ
     */
    Difficulty(final int value) {
        this.value = value;
    }

    /**
     * 列挙型を返却します。
     *
     * @author uratamanabu
     * @version 1.0
     * @return　難易度
     * @since 1.0
     */
    public static Difficulty getDifficulty(final String key) {
        if (key == null) {
            return null;
        }
        for (Difficulty difficulty : Difficulty.values()) {
            if (difficulty.toString().equals(key)) {
                return difficulty;
            }
        }
        return null;
    }
}
