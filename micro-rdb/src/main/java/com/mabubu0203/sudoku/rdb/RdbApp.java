package com.mabubu0203.sudoku.rdb;

import com.mabubu0203.sudoku.CommonCore;
import com.mabubu0203.sudoku.InterfacesCore;
import com.mabubu0203.sudoku.rdb.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Import;

/**
 * SpringBoot起動クラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@Import(value = {CommonCore.class, InterfacesCore.class})
@RefreshScope
public class RdbApp extends SpringBootServletInitializer {

    @Autowired
    private ApplicationConfig config;

    /**
     * SpringBoot起動methodです。<br>
     *
     * @param args 起動引数
     * @since 1.0
     */
    public static void main(final String[] args) {
        new SpringApplicationBuilder(RdbApp.class).run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(RdbApp.class);
    }

}
