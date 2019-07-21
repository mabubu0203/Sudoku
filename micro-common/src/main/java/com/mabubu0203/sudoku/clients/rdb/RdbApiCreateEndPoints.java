package com.mabubu0203.sudoku.clients.rdb;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
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
@Service
public class RdbApiCreateEndPoints {

    /**
     * {@code /}<br>
     *
     * @param restOperations
     * @param numberPlaceBean
     * @return boolean
     * @since 1.0
     */
    public Optional<String> insert(
            final RestOperations restOperations,
            final NumberPlaceBean numberPlaceBean
    ) {

        final String insert = "http://localhost:9011/SudokuRdb/"
                + CommonConstants.SLASH + RestUrlConstants.URL_CREATE_MASTER + CommonConstants.SLASH;
        try {
            URI uri = new URI(insert);
            RequestEntity requestEntity =
                    RequestEntity
                            .post(uri)
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .body(numberPlaceBean);
            ResponseEntity<String> generateEntity = restOperations.exchange(requestEntity, String.class);
            HttpStatus status = generateEntity.getStatusCode();
            switch (status) {
                case OK:
                    return Optional.ofNullable(generateEntity.getBody());
                case CONFLICT:
                default:
                    return Optional.empty();
            }
        } catch (URISyntaxException | RestClientException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
