package com.ezentwix.teamcostco.pagination;

import oracle.sql.TIMESTAMP;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

import org.mybatis.spring.SqlSessionTemplate;

@Slf4j
@Repository
public class PaginationRepository {
    private final SqlSessionTemplate sessionTemplate;
    private final ModelMapper modelMapper;

    // map의 시간 데이터 타입이 oracle.sql.TIMESTAMP 이기 때문에 따로 ojdbc 라이브러리를 가져와
    // date 형식으로 변환하는것이 필요함
    public PaginationRepository(SqlSessionTemplate sqlSessionTemplate) {
        this.sessionTemplate = sqlSessionTemplate;
        this.modelMapper = new ModelMapper();

        modelMapper.addConverter(new Converter<TIMESTAMP, Date>() {
            @Override
            public Date convert(MappingContext<TIMESTAMP, Date> context) {
                TIMESTAMP source = context.getSource();
                if (source != null) {
                    // TIMESTAMP를 long 값으로 변환한 후, Date로 변환
                    Date date = null;
                    try {
                        date = source.dateValue();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return date;
                }
                return null;
            }
        });
    }

    /**
     * 페이지네이션을 적용한 데이터를 가져옵니다.
     *
     * @param queryId          MyBatis 쿼리의 ID (count와 selectPage 쿼리에서 사용됨)
     * @param pageable         페이지네이션 정보 (page, size, sort 등)
     * @param additionalParams 추가 쿼리 파라미터 (ex: 필터 값)
     * @return 페이지데이터를 반환
     */
    public <T> PaginationResult<T> getPage(String query, String queryId, Pageable pageable,
            Map<String, Object> additionalParams,
            Class<T> dtoClass) {

        query = query.trim();

        if (pageable == null || pageable.getPageSize() <= 0 || query.isEmpty()) {
            return new PaginationResult<>(List.of(), 0, 1, 1, 1, 1);
        }

        int limit = pageable.getPageSize();

        Map<String, Object> params = new HashMap<>();
        params.put("queryId", queryId);
        params.put("query", query);

        int count = sessionTemplate.selectOne("pagination.count", params);

        int totalPages = Math.max((int) Math.ceil((double) count / limit), 1);
        int currentPage = pageable.getPageNumber();

        if(currentPage > totalPages){
            return new PaginationResult<>(List.of(), 0, 1, 1, 1, 1);
        }

        int showPageNum = 5;
        int startPageNumber = (currentPage - 1) / showPageNum * showPageNum + 1;
        int endPageNumber = Math.max(Math.min(startPageNumber + showPageNum - 1, totalPages), 1);

        int offset = Math.max(0, limit * (currentPage - 1));

        params.put("offset", offset);
        params.put("limit", limit);

        if (additionalParams != null) {
            params.putAll(additionalParams);
        }

        // Query data only if the current page is within the valid range
        List<Map<String, Object>> rawData;
        if (currentPage > 0 && currentPage <= totalPages) {
            rawData = sessionTemplate.selectList("pagination.selectPage", params);
        } else {
            rawData = List.of();
        }

        // Convert raw data to DTOs
        List<T> data = rawData.stream()
                .map(map -> modelMapper.map(map, dtoClass))
                .collect(Collectors.toList());

        return new PaginationResult<>(data, count, startPageNumber, endPageNumber, currentPage, totalPages);
    }

}
