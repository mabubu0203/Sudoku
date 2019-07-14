package com.mabubu0203.sudoku.api.service.impl;

import com.mabubu0203.sudoku.api.service.UpdateService;
import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.rdb.domain.ScoreInfoTbl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 更新する為のサービスクラスです。<br>
 * このクラスを経由してロジックを実行してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@AllArgsConstructor
@Service
public class UpdateServiceImpl implements UpdateService {

    @Override
    @Transactional
    public ResponseEntity<Long> updateScore(
            final RestOperations restOperations,
            final ScoreInfoTbl updateScoreBean,
            final int type,
            final String keyHash) {

        final String findByTypeAndKeyHash = "http://localhost:9011/SudokuRdb/"
                + CommonConstants.SLASH + "scoreInfoTbls" + CommonConstants.SLASH
                + "search" + CommonConstants.SLASH + "findByTypeAndKeyHash";
        final String update = "http://localhost:9011/SudokuRdb/"
                + CommonConstants.SLASH + "scoreInfoTbls" + CommonConstants.SLASH;

        // TODO:findByTypeAndKeyHash
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
                    break;
                case NOT_FOUND:
                default:
                    return new ResponseEntity<>(Long.MIN_VALUE, HttpStatus.BAD_REQUEST);
            }
            // TODO:update
            ScoreInfoTbl scoreInfoTbl = generateEntity.getBody();
            scoreInfoTbl.setName(updateScoreBean.getName());
            scoreInfoTbl.setMemo(updateScoreBean.getMemo());
            scoreInfoTbl.setScore(updateScoreBean.getScore());

            uri = new UriTemplate(update + "{no}").expand(scoreInfoTbl.getNo());
            requestEntity =
                    RequestEntity
                            .put(uri)
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .body(scoreInfoTbl);
            generateEntity = restOperations.exchange(requestEntity, ScoreInfoTbl.class);
            status = generateEntity.getStatusCode();
            switch (status) {
                case OK:
                    return new ResponseEntity<>(scoreInfoTbl.getNo(), HttpStatus.OK);
                case CONFLICT:
                    return new ResponseEntity<>(Long.MIN_VALUE, HttpStatus.CONFLICT);
                default:
                    return new ResponseEntity<>(Long.MIN_VALUE, HttpStatus.BAD_REQUEST);
            }

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<>(Long.MIN_VALUE, HttpStatus.BAD_REQUEST);
        }

    }

}
