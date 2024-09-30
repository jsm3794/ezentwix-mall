package com.ezentwix.teamcostco.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesDetailService implements PageMetadataProvider {

    @Override
    public String getUri() {
        return "/customer/sales/detail";
    }

    @Override
    public String getPageTitle() {
        return "주문상세";
    }

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/sales_detail.css");
    }

    @Override
    public List<String> getJsFiles() {
        return List.of("/js/contents/sales_detail.js");
    }

}
