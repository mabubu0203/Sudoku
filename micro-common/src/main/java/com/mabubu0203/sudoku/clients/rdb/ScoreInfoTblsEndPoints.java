package com.mabubu0203.sudoku.clients.rdb;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
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
 * {@code /scoreInfoTbls}のエンドポイントのラッパーです。<br>
 * このクラスを経由してWebApi呼び出しを行ってください。<br>
 * .
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Service
public class ScoreInfoTblsEndPoints {

    /**
     * {@code /search/findByTypeAndKeyHash}<br>
     *
     * @param restOperations
     * @param type
     * @param keyHash
     * @return
     * @since 1.0
     */
    public Optional<ScoreInfoTbl> findByTypeAndKeyHash(
            final RestOperations restOperations,
            final int type,
            final String keyHash) {

        final String findByTypeAndKeyHash = "http://localhost:9011/SudokuRdb/"
                + CommonConstants.SLASH + "scoreInfoTbls" + CommonConstants.SLASH
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
            ResponseEntity<ScoreInfoTbl> generateEntity = restOperations.exchange(requestEntity, ScoreInfoTbl.class);
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
     * {@code /}<br>
     *
     * @param restOperations
     * @param numberPlaceBean
     * @return boolean
     * @since 1.0
     */
    public boolean insert(
            final RestOperations restOperations,
            final NumberPlaceBean numberPlaceBean) {
        final String insert = "http://localhost:9011/SudokuRdb/"
                + CommonConstants.SLASH + "scoreInfoTbls" + CommonConstants.SLASH;

        try {
            URI uri = new URI(insert);
            RequestEntity requestEntity =
                    RequestEntity
                            .post(uri)
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .body(numberPlaceBean);
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
     * {@code /}<br>
     *
     * @param restOperations
     * @param updateScoreBean
     * @return boolean
     * @since 1.0
     */
    public boolean update(
            final RestOperations restOperations,
            final ScoreInfoTbl updateScoreBean) {
        final String update = "http://localhost:9011/SudokuRdb/"
                + CommonConstants.SLASH + "scoreInfoTbls" + CommonConstants.SLASH;

        URI uri = new UriTemplate(update + "{no}").expand(updateScoreBean.getNo());
        try {
            RequestEntity requestEntity =
                    RequestEntity
                            .put(uri)
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .body(updateScoreBean);
            ResponseEntity<ScoreInfoTbl> generateEntity = restOperations.exchange(requestEntity, ScoreInfoTbl.class);
            HttpStatus status = generateEntity.getStatusCode();
            switch (status) {
                case OK:
                    return true;
                case CONFLICT:
                default:
                    return false;
            }
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return false;
        }
    }


}
