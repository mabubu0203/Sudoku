package com.mabubu0203.sudoku.utils;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.enums.Difficulty;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static lombok.AccessLevel.PRIVATE;

/**
 * 数独に使用するユーティル群をまとめたクラスです。<br>
 * .
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class SudokuUtils {

    /**
     * 引数で与えられた整数の平方根を返します。<br>
     *
     * @param num
     * @return 平方根
     */
    public static int convertSquareRoot(int num) {
        return (int) Math.sqrt(num);
    }

    /**
     * 虫食い数を取得する。<br>
     *
     * @param selectType
     * @param selectLevel
     * @return 虫食い数
     */
    public static int getWarmEatenValue(int selectType, String selectLevel) {
        String key = selectLevel.toUpperCase().concat(Integer.toString(selectType));
        Difficulty difficulty = Difficulty.getDifficulty(key);
        if (difficulty == null) {
            return 0;
        } else {
            return difficulty.getValue();
        }
    }

    /**
     * TypeとLebelから最大スコアを算出します。<br>
     *
     * @param selectType
     * @param selectLevel
     * @return 最大スコア
     */
    public static int calculationScore(int selectType, String selectLevel) {
        String key = selectLevel.toUpperCase().concat(Integer.toString(selectType));
        Difficulty difficulty = Difficulty.getDifficulty(key);
        if (difficulty == null) {
            throw new SudokuApplicationException();
        }
        switch (difficulty) {
            case EASY4:
            case EASY9:
                return 2000;
            case NORMAL4:
            case NORMAL9:
                return 5000;
            case HARD4:
                return 10000;
            case HARD9:
                return 15000;
            default:
                throw new SudokuApplicationException();
        }
    }

    /**
     * SHA256変換した文字列を返却します。<br>
     *
     * @param str 文字列
     * @return SHA256変換した文字列かnull
     */
    public static String convertToSha256(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes(CommonConstants.UTF8));
            byte[] cipherByte = md.digest();
            StringBuilder hash = new StringBuilder(2 * cipherByte.length);
            for (byte b : cipherByte) {
                hash.append(String.format("%02x", b & 0xff));
            }
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return CommonConstants.EMPTY_STR;
        }
    }

}
