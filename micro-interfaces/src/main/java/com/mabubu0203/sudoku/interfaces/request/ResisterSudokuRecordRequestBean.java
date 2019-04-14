package com.mabubu0203.sudoku.interfaces.request;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import lombok.Data;

import java.io.Serializable;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
public class ResisterSudokuRecordRequestBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private NumberPlaceBean numberPlaceBean;

}
