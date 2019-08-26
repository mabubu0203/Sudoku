package com.mabubu0203.sudoku.clients.rdb;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
public class RdbApiSearchEndPoints {

    /**
     * {@code /}<br>
     *
     * @param restOperations
     * @param conditionBean
     * @return boolean
     * @since 1.0
     */
    public PagedResources<Resource<AnswerInfoTbl>> search(
            final RestOperations restOperations,
            final SearchConditionBean conditionBean,
            final Pageable pageable) {

        final String search = "http://localhost:9011/SudokuRdb/"
                + RestUrlConstants.URL_SEARCH_MASTER + CommonConstants.SLASH;

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("selectType", Integer.toString(conditionBean.getType()));
        uriVariables.put("selectorNo", Integer.toString(conditionBean.getSelectorNo()));
        uriVariables.put("selectorKeyHash", Integer.toString(conditionBean.getSelectorKeyHash()));
        uriVariables.put("selectorScore", Integer.toString(conditionBean.getSelectorScore()));
        uriVariables.put("selectorName", Integer.toString(conditionBean.getSelectorName()));
        uriVariables.put("number", Integer.toString(pageable.getPageNumber()));
        uriVariables.put("size", Integer.toString(pageable.getPageSize()));
        URI uri = new UriTemplate(search + "?selectType={selectType}&selectorNo={selectorNo}&selectorKeyHash={selectorKeyHash}&selectorScore={selectorScore}&selectorName={selectorName}&number={number}&size={size}").expand(uriVariables);
        RequestEntity requestEntity = RequestEntity
                .get(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .build();
        try {
            ResponseEntity<PagedResources<Resource<AnswerInfoTbl>>> generateEntity = restOperations.exchange(
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
            log.info(generateEntity.getBody().toString());
            return generateEntity.getBody();
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return null;
        }
    }

}
