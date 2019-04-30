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

    private Long no;
    private Integer type;
    private String answerKey;

    @Size(max = 64)
    private String keyHash;

    private Integer a1;
    private Integer a2;
    private Integer a3;
    private Integer a4;
    private Integer a5;
    private Integer a6;
    private Integer a7;
    private Integer a8;
    private Integer a9;

    private Integer b1;
    private Integer b2;
    private Integer b3;
    private Integer b4;
    private Integer b5;
    private Integer b6;
    private Integer b7;
    private Integer b8;
    private Integer b9;

    private Integer c1;
    private Integer c2;
    private Integer c3;
    private Integer c4;
    private Integer c5;
    private Integer c6;
    private Integer c7;
    private Integer c8;
    private Integer c9;

    private Integer d1;
    private Integer d2;
    private Integer d3;
    private Integer d4;
    private Integer d5;
    private Integer d6;
    private Integer d7;
    private Integer d8;
    private Integer d9;

    private Integer e1;
    private Integer e2;
    private Integer e3;
    private Integer e4;
    private Integer e5;
    private Integer e6;
    private Integer e7;
    private Integer e8;
    private Integer e9;

    private Integer f1;
    private Integer f2;
    private Integer f3;
    private Integer f4;
    private Integer f5;
    private Integer f6;
    private Integer f7;
    private Integer f8;
    private Integer f9;

    private Integer g1;
    private Integer g2;
    private Integer g3;
    private Integer g4;
    private Integer g5;
    private Integer g6;
    private Integer g7;
    private Integer g8;
    private Integer g9;

    private Integer h1;
    private Integer h2;
    private Integer h3;
    private Integer h4;
    private Integer h5;
    private Integer h6;
    private Integer h7;
    private Integer h8;
    private Integer h9;

    private Integer i1;
    private Integer i2;
    private Integer i3;
    private Integer i4;
    private Integer i5;
    private Integer i6;
    private Integer i7;
    private Integer i8;
    private Integer i9;

}
