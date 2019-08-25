package com.mabubu0203.sudoku.clients.api;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.PathParameterConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Optional;

/**
 * {@code /createMaster}のエンドポイントのラッパーです。<br>
 * このクラスを経由してWebApi呼び出しを行ってください。<br>
 * .
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
public class RestApiCreateEndPoints {

    /**
     * {@code /{type}}<br>
     *
     * @param restOperations
     * @param type
     * @return boolean
     * @since 1.0
     */
    public Optional<String> createMaster(
            final RestOperations restOperations,
            final int type
    ) {

        final String generate = "http://localhost:9001/SudokuApi/"
                + RestUrlConstants.URL_CREATE_MASTER + CommonConstants.SLASH + PathParameterConstants.PATH_TYPE;

        URI uri = new UriTemplate(generate).expand(type);
        RequestEntity requestEntity = RequestEntity
                .get(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .build();
        try {
            ResponseEntity<String> generateEntity = restOperations.exchange(requestEntity, String.class);
            return Optional.ofNullable(generateEntity.getBody());
        } catch (HttpClientErrorException e) {
            HttpStatus status = e.getStatusCode();
            switch (status) {
                case CONFLICT:
                    log.info("衝突しています。");
                    return Optional.empty();
                default:
                    return Optional.empty();
            }
        }
    }

}
