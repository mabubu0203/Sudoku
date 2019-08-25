package com.mabubu0203.sudoku.clients.api;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.interfaces.request.UpdateSudokuScoreRequestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * {@code /updateMaster}のエンドポイントのラッパーです。<br>
 * このクラスを経由してWebApi呼び出しを行ってください。<br>
 * .
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
public class RestApiUpdateEndPoints {

    /**
     * {@code /score}<br>
     *
     * @param restOperations
     * @param request
     * @return boolean
     * @since 1.0
     */
    public Optional<Long> update(
            final RestOperations restOperations,
            final UpdateSudokuScoreRequestBean request
    ) {

        final String update = "http://localhost:9001/SudokuApi/"
                + RestUrlConstants.URL_UPDATE_MASTER + CommonConstants.SLASH
                + RestUrlConstants.URL_SCORE + CommonConstants.SLASH;
        try {
            URI uri = new URI(update);
            RequestEntity requestEntity =
                    RequestEntity
                            .put(uri)
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .body(request);
            ResponseEntity<Long> generateEntity = restOperations.exchange(requestEntity, Long.class);
            return Optional.ofNullable(generateEntity.getBody());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return Optional.empty();
        } catch (HttpClientErrorException e) {
            HttpStatus status = e.getStatusCode();
            switch (status) {
                case BAD_REQUEST:
                    log.info("???");
                    log.info(e.getMessage());
                    return Optional.empty();
                default:
                    return Optional.empty();
            }
        }
    }

}
