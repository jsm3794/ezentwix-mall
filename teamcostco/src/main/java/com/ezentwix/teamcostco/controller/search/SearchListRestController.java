package com.ezentwix.teamcostco.controller.search;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezentwix.teamcostco.dto.filter.ProductFilterDTO;
import com.ezentwix.teamcostco.dto.product.ProductDTO;
import com.ezentwix.teamcostco.pagination.PaginationResult;
import com.ezentwix.teamcostco.service.SearchListService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SearchListRestController {
    private final SearchListService searchListService;
    private final ObjectMapper objectMapper;

    @PostMapping("/product/search")
    public PaginationResult<ProductDTO> showSearchList(
            @RequestParam(value = "query", defaultValue = "") String query,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "16") Integer size,
            @ModelAttribute ProductFilterDTO productFilterDTO) {

        Map<String, Object> map = objectMapper.convertValue(productFilterDTO, Map.class);
        PaginationResult<ProductDTO> result = searchListService.getPage(query, page, size, map);

        return result;
    }
}
