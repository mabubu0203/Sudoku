package com.mabubu0203.sudoku.components;

/**
 * 数独の解答を生成する為のinterfaceです。<br>
 * このinterfaceを経由してロジックを実行してください。<br>
 * 実装については実装クラスを参照してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface SudokuGenerator {

    /**
     * typeから数独を生成します。<br>
     *
     * @param type
     * @author uratamanabu
     * @since 1.0
     */
    void generate(final int type);

}
