package com.mabubu0203.sudoku.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * SpringBoot起動クラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigApp extends SpringBootServletInitializer {

    /**
     * SpringBoot起動methodです。<br>
     *
     * @param args 起動引数
     * @author uratamanabu
     * @since 1.0
     */
    public static void main(final String[] args) {
        new SpringApplicationBuilder(ConfigApp.class).profiles("dev").run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(ConfigApp.class);
    }

}
