package com.mabubu0203.sudoku.api;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Configuration
@ComponentScan
@EntityScan(basePackageClasses = {ApiCore.class})
public class ApiCore {

}
