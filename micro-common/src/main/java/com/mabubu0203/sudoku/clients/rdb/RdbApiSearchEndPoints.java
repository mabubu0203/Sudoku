package com.mabubu0203.sudoku.clients.rdb;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
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
public class RdbApiSearchEndPoints {

    /**
     * {@code /}<br>
     *
     * @param restOperations
     * @param conditionBean
     * @return boolean
     * @since 1.0
     */
    public Page<AnswerInfoTbl> search(
            final RestOperations restOperations,
            final SearchConditionBean conditionBean) {
        final String search = "http://localhost:9011/SudokuRdb/"
                + CommonConstants.SLASH + RestUrlConstants.URL_SEARCH_MASTER + CommonConstants.SLASH;

        try {
            URI uri = new URI(search);
            RequestEntity requestEntity =
                    RequestEntity
                            .post(uri)
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .body(search);
            ResponseEntity<Page<AnswerInfoTbl>> generateEntity = restOperations.exchange(requestEntity, new ParameterizedTypeReference<Page<AnswerInfoTbl>>() {
            });
            HttpStatus status = generateEntity.getStatusCode();
            switch (status) {
                case OK:
                    return generateEntity.getBody();
                case CONFLICT:
                default:
                    return null;
            }
        } catch (URISyntaxException | RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }

}
