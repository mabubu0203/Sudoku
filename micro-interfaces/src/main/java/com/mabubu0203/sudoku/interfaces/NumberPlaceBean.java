package com.mabubu0203.sudoku.interfaces;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 数独を保持するBeanです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
public class NumberPlaceBean implements NumberPlaceDefine, Serializable {

    private static final long serialVersionUID = 1L;

    private long no;
    private int type;
    private String answerKey;

    @Size(max = 64)
    private String keyHash;

    private int a1;
    private int a2;
    private int a3;
    private int a4;
    private int a5;
    private int a6;
    private int a7;
    private int a8;
    private int a9;

    private int b1;
    private int b2;
    private int b3;
    private int b4;
    private int b5;
    private int b6;
    private int b7;
    private int b8;
    private int b9;

    private int c1;
    private int c2;
    private int c3;
    private int c4;
    private int c5;
    private int c6;
    private int c7;
    private int c8;
    private int c9;

    private int d1;
    private int d2;
    private int d3;
    private int d4;
    private int d5;
    private int d6;
    private int d7;
    private int d8;
    private int d9;

    private int e1;
    private int e2;
    private int e3;
    private int e4;
    private int e5;
    private int e6;
    private int e7;
    private int e8;
    private int e9;

    private int f1;
    private int f2;
    private int f3;
    private int f4;
    private int f5;
    private int f6;
    private int f7;
    private int f8;
    private int f9;

    private int g1;
    private int g2;
    private int g3;
    private int g4;
    private int g5;
    private int g6;
    private int g7;
    private int g8;
    private int g9;

    private int h1;
    private int h2;
    private int h3;
    private int h4;
    private int h5;
    private int h6;
    private int h7;
    private int h8;
    private int h9;

    private int i1;
    private int i2;
    private int i3;
    private int i4;
    private int i5;
    private int i6;
    private int i7;
    private int i8;
    private int i9;

}
