package com.mabubu0203.sudoku.web;

import com.mabubu0203.sudoku.CommonCore;
import com.mabubu0203.sudoku.web.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/**
 * SpringBoot起動クラスです。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@Import(value = {CommonCore.class})
@EntityScan(basePackageClasses = {WebApp.class})
@EnableScheduling
@RefreshScope
public class WebApp extends SpringBootServletInitializer implements SchedulingConfigurer {

    @Autowired
    private ApplicationConfig config;

    /**
     * SpringBoot起動methodです。<br>
     *
     * @param args 起動引数
     * @author uratamanabu
     * @since 1.0
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(WebApp.class).run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebApp.class);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler());
    }

    /**
     * <br>
     *
     * @return Executor
     * @author uratamanabu
     * @since 1.0
     */
    @Bean(destroyMethod = "")
    public Executor taskScheduler() {
        ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();
        threadPool.setPoolSize(5);

        // shutdown 時に Task の完了を待つ（最大10分間）
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setAwaitTerminationSeconds(60 * 10);
        return threadPool;
    }

}
