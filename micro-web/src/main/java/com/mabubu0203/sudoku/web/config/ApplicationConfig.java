package com.mabubu0203.sudoku.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {

    @Value("${system.name:not-found}")
    @Getter
    @Setter
    private String systemName;

}
