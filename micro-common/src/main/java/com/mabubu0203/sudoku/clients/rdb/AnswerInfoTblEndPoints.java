package com.mabubu0203.sudoku.clients.rdb;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.interfaces.domain.ScoreInfoTbl;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.net.URISyntaxException;

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
public class AnswerInfoTblEndPoints {

    /**
     * {@code /}<br>
     *
     * @param restOperations
     * @param answerInfoTbl
     * @return boolean
     * @since 1.0
     */
    public boolean insert(
            final RestOperations restOperations,
            final AnswerInfoTbl answerInfoTbl) {
        final String insert = "http://localhost:9011/SudokuRdb/"
                + CommonConstants.SLASH + "answerInfoTbls" + CommonConstants.SLASH;

        try {
            URI uri = new URI(insert);
            RequestEntity requestEntity =
                    RequestEntity
                            .post(uri)
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .body(answerInfoTbl);
            ResponseEntity<ScoreInfoTbl> generateEntity = restOperations.exchange(requestEntity, ScoreInfoTbl.class);
            HttpStatus status = generateEntity.getStatusCode();
            switch (status) {
                case OK:
                    return true;
                case CONFLICT:
                default:
                    return false;
            }
        } catch (URISyntaxException | RestClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
