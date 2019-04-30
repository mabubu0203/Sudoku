package com.mabubu0203.sudoku.api.controller;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.PathParameterConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.controller.RestBaseController;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.request.ResisterSudokuRecordRequestBean;
import com.mabubu0203.sudoku.validator.constraint.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping(
        value = {CommonConstants.SLASH + RestUrlConstants.URL_CREATE_MASTER + CommonConstants.SLASH},
        produces = "application/json"
)
public class RestApiMasterController extends RestBaseController {

    private final RestOperations restOperations;

    public RestApiMasterController(final RestTemplateBuilder restTemplateBuilder) {
        this.restOperations = restTemplateBuilder.build();
    }

    /**
     * Web側から呼び出されます。<br>
     *
     * @param type
     * @param uriComponentsBuilder
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(value = {PathParameterConstants.PATH_TYPE})
    public ResponseEntity<String> createFromWeb(
            @PathVariable(name = "type") @Type final Integer type,
            final UriComponentsBuilder uriComponentsBuilder) {

        log.info("Creating From Web");
        URI uri;
        RequestEntity requestEntity;

        uri =
                uriComponentsBuilder
                        .cloneBuilder()
                        .pathSegment(
                                RestUrlConstants.URL_CREATE_MASTER,
                                RestUrlConstants.URL_GENERATE,
                                PathParameterConstants.PATH_TYPE)
                        .buildAndExpand(type)
                        .toUri();
        requestEntity = RequestEntity.get(uri).build();
        ResponseEntity<NumberPlaceBean> generateEntity =
                restOperations.exchange(requestEntity, NumberPlaceBean.class);
        NumberPlaceBean numberPlaceBean = generateEntity.getBody();
        String answerKey = numberPlaceBean.getAnswerKey();
        uri =
                uriComponentsBuilder
                        .cloneBuilder()
                        .pathSegment(
                                RestUrlConstants.URL_SEARCH_MASTER, PathParameterConstants.PATH_TYPEANSWER_KEY)
                        .buildAndExpand(answerKey)
                        .toUri();
        requestEntity = RequestEntity.get(uri).build();
        ResponseEntity<Boolean> isSudokuExistEntity =
                restOperations.exchange(requestEntity, Boolean.class);
        if (isSudokuExistEntity.getBody().booleanValue()) {
            log.error("一意制約違反です。");
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        } else {
            ResisterSudokuRecordRequestBean request = new ResisterSudokuRecordRequestBean();
            request.setNumberPlaceBean(numberPlaceBean);
            uri =
                    uriComponentsBuilder
                            .cloneBuilder()
                            .pathSegment(RestUrlConstants.URL_CREATE_MASTER, RestUrlConstants.URL_GENERATE)
                            .build()
                            .toUri();
            requestEntity = RequestEntity.post(uri).body(request);
            return restOperations.exchange(requestEntity, String.class);
        }
    }

}
