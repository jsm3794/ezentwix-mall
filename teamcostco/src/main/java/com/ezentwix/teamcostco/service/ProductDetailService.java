package com.ezentwix.teamcostco.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductDetailService implements PageMetadataProvider {

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/product_detail.css");
    }

    @Override
    public List<String> getJsFiles() {
        return List.of("/js/contents/product_detail.js");
    }

    @Override
    public String getUri() {
        return "product/product_detail";
    }
    
    @Override
    public String getPageTitle() {
        return "";
    }
}
