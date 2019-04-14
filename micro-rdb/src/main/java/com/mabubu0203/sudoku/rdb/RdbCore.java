package com.mabubu0203.sudoku.rdb;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Configuration
@ComponentScan
@EntityScan(basePackages = {"com.mabubu0203.sudoku.rdb.domain"})
@EnableJpaRepositories(basePackages = {"com.mabubu0203.sudoku.rdb.repository"})
@EnableTransactionManagement(proxyTargetClass = true)
public class RdbCore {
}
