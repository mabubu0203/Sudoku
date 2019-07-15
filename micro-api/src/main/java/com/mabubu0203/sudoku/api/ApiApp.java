package com.mabubu0203.sudoku.api;

import com.mabubu0203.sudoku.CommonCore;
import com.mabubu0203.sudoku.InterfacesCore;
import com.mabubu0203.sudoku.api.config.ApplicationConfig;
import com.mabubu0203.sudoku.rdb.RdbCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBoot起動クラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@Import(value = {InterfacesCore.class, CommonCore.class, RdbCore.class})
@EnableScheduling
@RefreshScope
public class ApiApp extends SpringBootServletInitializer {

    @Autowired
    private ApplicationConfig config;

    /**
     * SpringBoot起動methodです。<br>
     *
     * @param args 起動引数
     * @author uratamanabu
     * @since 1.0
     */
    public static void main(final String[] args) {
        new SpringApplicationBuilder(ApiApp.class).run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(ApiApp.class);
    }

}
