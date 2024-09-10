package com.ezentwix.teamcostco.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezentwix.teamcostco.dto.product.ProductDTO;
import com.ezentwix.teamcostco.pagination.PaginationResult;
import com.ezentwix.teamcostco.service.SearchListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SearchListApiController {
    private final SearchListService searchListService;

    @PostMapping("/search")
    public List<ProductDTO> showSearchList(
            @RequestParam(value = "query", defaultValue = "") String query,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "50") Integer size) {

        PaginationResult<ProductDTO> result = searchListService.getPage(query, page, size, null);

        return result.getData();
    }
}
