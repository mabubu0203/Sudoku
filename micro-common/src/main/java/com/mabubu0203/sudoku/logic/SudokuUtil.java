package com.mabubu0203.sudoku.logic;


import com.mabubu0203.sudoku.constants.CommonConstants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface SudokuUtil {

  /**
   * @param numberPlace
   * @return
   */
  default String createAnswerKey(int[][] numberPlace) {
    StringBuilder answerKey = new StringBuilder();
    for (int[] arrays : numberPlace) {
      for (int array : arrays) {
        answerKey.append(array);
      }
    }
    return answerKey.toString();
  }

  /**
   * SHA256変換した文字列を返却します。
   *
   * @param str 文字列
   * @return SHA256変換した文字列かnull
   */
  default String convertToSha256(String str) {
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
      return null;
    }
  }
}
