package com.ezentwix.teamcostco.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;

@Service
public class PurchaseService implements PageMetadataProvider {

    @Override
    public String getUri() {
        return "purchase/purchase";
    }

    @Override
    public String getPageTitle() {
        return "상품주문";
    }

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/purchase.css");
    }

    @Override
    public List<String> getJsFiles() {
        return List.of("/js/contents/purchase.js");
    }

}
