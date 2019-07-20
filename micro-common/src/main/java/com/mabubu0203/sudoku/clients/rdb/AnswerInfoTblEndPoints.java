package com.mabubu0203.sudoku.clients.rdb;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.interfaces.domain.ScoreInfoTbl;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
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

    /**
     * {@code /search/findFirstByType}<br>
     *
     * @param restOperations
     * @param type
     * @return
     * @since 1.0
     */
    public Optional<AnswerInfoTbl> findFirstByType(
            final RestOperations restOperations,
            final int type) {
        final String findByTypeAndKeyHash = "http://localhost:9011/SudokuRdb/"
                + CommonConstants.SLASH + "answerInfoTbls" + CommonConstants.SLASH
                + "search" + CommonConstants.SLASH + "findFirstByType";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("type", Integer.toString(type));
        URI uri = new UriTemplate(findByTypeAndKeyHash + "?type={type}").expand(uriVariables);
        RequestEntity requestEntity =
                RequestEntity
                        .get(uri)
                        .header(HttpHeaders.CONTENT_TYPE, "application/hal+json;charset=UTF-8")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .build();

        try {
            ResponseEntity<AnswerInfoTbl> generateEntity = restOperations.exchange(requestEntity, AnswerInfoTbl.class);
            HttpStatus status = generateEntity.getStatusCode();
            switch (status) {
                case OK:
                    return Optional.of(generateEntity.getBody());
                case NOT_FOUND:
                default:
                    return Optional.ofNullable(null);
            }
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return Optional.ofNullable(null);
        }
    }

    /**
     * {@code /search/findByAnswerKey}<br>
     *
     * @param restOperations
     * @param answerKey
     * @return
     * @since 1.0
     */
    public AnswerInfoTbl findByAnswerKey(
            final RestOperations restOperations,
            final String answerKey) {
        final String findByAnswerKey = "http://localhost:9011/SudokuRdb/"
                + CommonConstants.SLASH + "answerInfoTbls" + CommonConstants.SLASH
                + "search" + CommonConstants.SLASH + "findByAnswerKey";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("findByAnswerKey", answerKey);
        URI uri = new UriTemplate(findByAnswerKey + "?answerKey={answerKey}").expand(uriVariables);
        RequestEntity requestEntity =
                RequestEntity
                        .get(uri)
                        .header(HttpHeaders.CONTENT_TYPE, "application/hal+json;charset=UTF-8")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .build();
        try {
            ResponseEntity<AnswerInfoTbl> generateEntity = restOperations.exchange(requestEntity, AnswerInfoTbl.class);
            HttpStatus status = generateEntity.getStatusCode();
            switch (status) {
                case OK:
                    return generateEntity.getBody();
                case NOT_FOUND:
                default:
                    return null;
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * {@code /search/findByTypeAndKeyHash}<br>
     *
     * @param restOperations
     * @param type
     * @param keyHash
     * @return
     * @since 1.0
     */
    public Optional<AnswerInfoTbl> findByTypeAndKeyHash(
            final RestOperations restOperations,
            final int type,
            final String keyHash) {

        final String findByTypeAndKeyHash = "http://localhost:9011/SudokuRdb/"
                + CommonConstants.SLASH + "answerInfoTbls" + CommonConstants.SLASH
                + "search" + CommonConstants.SLASH + "findByTypeAndKeyHash";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("type", Integer.toString(type));
        uriVariables.put("keyHash", keyHash);
        URI uri = new UriTemplate(findByTypeAndKeyHash + "?type={type}&keyHash={keyHash}").expand(uriVariables);
        RequestEntity requestEntity =
                RequestEntity
                        .get(uri)
                        .header(HttpHeaders.CONTENT_TYPE, "application/hal+json;charset=UTF-8")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .build();

        try {
            ResponseEntity<AnswerInfoTbl> generateEntity = restOperations.exchange(requestEntity, AnswerInfoTbl.class);
            HttpStatus status = generateEntity.getStatusCode();
            switch (status) {
                case OK:
                    return Optional.of(generateEntity.getBody());
                case NOT_FOUND:
                default:
                    return Optional.ofNullable(null);
            }
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return Optional.ofNullable(null);
        }
    }

}
