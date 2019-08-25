package com.mabubu0203.sudoku.clients.rdb;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.interfaces.domain.ScoreInfoTbl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDateTime;
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
@Slf4j
@Service
public class ScoreInfoTblEndPoints {

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
                + "scoreInfoTbls" + CommonConstants.SLASH
                + "search" + CommonConstants.SLASH + "findByTypeAndKeyHash";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("type", Integer.toString(type));
        uriVariables.put("keyHash", keyHash);
        URI uri = new UriTemplate(findByTypeAndKeyHash + "?type={type}&keyHash={keyHash}").expand(uriVariables);
        RequestEntity requestEntity = RequestEntity
                .get(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE)
                .build();

        try {
            ResponseEntity<ScoreInfoTbl> generateEntity = restOperations.exchange(requestEntity, ScoreInfoTbl.class);
            return Optional.of(generateEntity.getBody());
        } catch (HttpClientErrorException e) {
            HttpStatus status = e.getStatusCode();
            switch (status) {
                case NOT_FOUND:
                    log.info("見つかりませんでした。");
                default:
                    return Optional.empty();
            }
        }
    }

    /**
     * {@code /{no}}<br>
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
                + "scoreInfoTbls" + CommonConstants.SLASH;

        updateScoreBean.setUpdateDate(LocalDateTime.now());
        URI uri = new UriTemplate(update + "{no}").expand(updateScoreBean.getNo());
        try {
            RequestEntity requestEntity = RequestEntity
                    .put(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE)
                    .header(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE)
                    .body(updateScoreBean);
            ResponseEntity<ScoreInfoTbl> generateEntity = restOperations.exchange(requestEntity, ScoreInfoTbl.class);
            log.info(generateEntity.getBody().toString());
            return true;
        } catch (HttpClientErrorException e) {
            HttpStatus status = e.getStatusCode();
            switch (status) {
                case CONFLICT:
                    log.info("衝突しています。");
                default:
                    return false;
            }
        }
    }

}
