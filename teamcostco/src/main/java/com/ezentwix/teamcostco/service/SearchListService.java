package com.ezentwix.teamcostco.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;
import com.ezentwix.teamcostco.dto.product.ProductDTO;
import com.ezentwix.teamcostco.pagination.PaginationRepository;
import com.ezentwix.teamcostco.pagination.PaginationResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchListService implements PageMetadataProvider {
    private final PaginationRepository paginationRepository;
    private String title = "";

    public PaginationResult<ProductDTO> getPage(String query, Integer page, Integer size, Map<String, Object> params) {
        return paginationRepository.getPage(query, "Products.getAll", PageRequest.of(page, size), params,
                ProductDTO.class);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getUri() {
        return "/search/search_product";
    }

    @Override
    public String getPageTitle() {
        return title;
    }

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/component/filter.css",
                "/css/contents/search.css");
    }

}
