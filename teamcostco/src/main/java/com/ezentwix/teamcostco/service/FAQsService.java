package com.ezentwix.teamcostco.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;
import com.ezentwix.teamcostco.dto.faq.FAQsCategoryDTO;
import com.ezentwix.teamcostco.dto.faq.FAQsDTO;
import com.ezentwix.teamcostco.repository.FAQsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FAQsService implements PageMetadataProvider {
    private final FAQsRepository faqsRepository;

    public List<FAQsDTO> getAllFAQList() {
        return faqsRepository.getAllFAQList();
    }

    public List<FAQsCategoryDTO> getAllCategoryList() {
        return faqsRepository.getAllCategoryList();
    }

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

    @Override
    public List<String> getJsFiles() {
        return List.of("/js/contents/FAQs.js");
    }

}
