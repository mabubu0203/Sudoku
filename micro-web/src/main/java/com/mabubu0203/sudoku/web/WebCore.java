package com.mabubu0203.sudoku.web;

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
@EntityScan(basePackageClasses = {WebCore.class})
public class WebCore {

}
