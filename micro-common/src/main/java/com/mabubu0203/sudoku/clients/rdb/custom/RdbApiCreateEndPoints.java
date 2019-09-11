package com.mabubu0203.sudoku.clients.rdb.custom;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * {@code /answerInfoTbls}のエンドポイントのラッパーです。<br>
 * このクラスを経由してWebApi呼び出しを行ってください。<br>
 * .
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
public class RdbApiCreateEndPoints {

    @Value("${sudoku.uri.rdb}")
    private String sudokuUriApi;

    /**
     * {@code /createMaster}<br>
     *
     * @param restOperations
     * @param numberPlaceBean
     * @return Optional
     * @since 1.0
     */
    public Optional<String> insert(
            final RestOperations restOperations,
            final NumberPlaceBean numberPlaceBean
    ) {

        final String insert = sudokuUriApi + RestUrlConstants.URL_CREATE_MASTER + CommonConstants.SLASH;

        try {
            URI uri = new URI(insert);
            RequestEntity requestEntity = RequestEntity
                    .post(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .body(numberPlaceBean);
            ResponseEntity<String> generateEntity = restOperations.exchange(requestEntity, String.class);
            return Optional.ofNullable(generateEntity.getBody());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return Optional.empty();
        } catch (HttpClientErrorException e) {
            HttpStatus status = e.getStatusCode();
            switch (status) {
                case CONFLICT:
                    log.info("衝突しています。");
                default:
                    return Optional.empty();
            }
        }
    }

}
