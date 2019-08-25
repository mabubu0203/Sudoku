package com.mabubu0203.sudoku.clients.rdb;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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
public class AnswerInfoTblEndPoints {

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
        final String findFirstByType = "http://localhost:9011/SudokuRdb/"
                + "answerInfoTbls" + CommonConstants.SLASH
                + "search" + CommonConstants.SLASH + "findFirstByType";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("type", Integer.toString(type));
        URI uri = new UriTemplate(findFirstByType + "?type={type}").expand(uriVariables);
        RequestEntity requestEntity =
                RequestEntity
                        .get(uri)
                        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE)
                        .header(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE)
                        .build();

        try {
            ResponseEntity<AnswerInfoTbl> generateEntity = restOperations.exchange(requestEntity, AnswerInfoTbl.class);
            HttpStatus status = generateEntity.getStatusCode();
            switch (status) {
                case OK:
                    return Optional.of(generateEntity.getBody());
                case NOT_FOUND:
                default:
                    return Optional.empty();
            }
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return Optional.empty();
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
    public List<AnswerInfoTbl> findByAnswerKey(
            final RestOperations restOperations,
            final String answerKey) {
        final String findByAnswerKey = "http://localhost:9011/SudokuRdb/"
                + "answerInfoTbls" + CommonConstants.SLASH
                + "search" + CommonConstants.SLASH + "findByAnswerKey";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("answerKey", answerKey);
        URI uri = new UriTemplate(findByAnswerKey + "?answerKey={answerKey}").expand(uriVariables);
        RequestEntity requestEntity =
                RequestEntity
                        .get(uri)
                        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE)
                        .header(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE)
                        .build();
        try {
            ResponseEntity<PagedResources<Resource<AnswerInfoTbl>>> generateEntity = restOperations.exchange(
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
            HttpStatus status = generateEntity.getStatusCode();
            switch (status) {
                case OK:
                    return generateEntity.getBody().getContent()
                            .stream()
                            .map(Resource::getContent)
                            .collect(toList());
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

        final String findByTypeAndKeyhash = "http://localhost:9011/SudokuRdb/"
                + "answerInfoTbls" + CommonConstants.SLASH
                + "search" + CommonConstants.SLASH + "findByTypeAndKeyHash";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("type", Integer.toString(type));
        uriVariables.put("keyHash", keyHash);
        URI uri = new UriTemplate(findByTypeAndKeyhash + "?type={type}&keyHash={keyHash}").expand(uriVariables);
        RequestEntity requestEntity = RequestEntity
                        .get(uri)
                        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE)
                        .header(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE)
                        .build();
        try {
            ResponseEntity<AnswerInfoTbl> generateEntity = restOperations.exchange(requestEntity, AnswerInfoTbl.class);
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

}