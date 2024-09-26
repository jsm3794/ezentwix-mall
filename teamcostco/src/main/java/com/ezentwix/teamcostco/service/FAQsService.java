package com.ezentwix.teamcostco.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FAQsService implements PageMetadataProvider {
    @Override
    public String getUri() {
        return "customer/FAQs";
    }

    @Override
    public String getPageTitle() {
        return "자주 묻는 질문";
    }

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/FAQs.css");
    }

}
