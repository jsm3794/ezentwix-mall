package com.ezentwix.teamcostco.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;
import com.ezentwix.teamcostco.dto.faq.FAQsDTO;
import com.ezentwix.teamcostco.repository.FAQsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactUsService implements PageMetadataProvider {
    
    private final FAQsRepository faqsRepository;

    public List<FAQsDTO> getAllFAQList() {
        return faqsRepository.getAllFAQList();
    }

    @Override
    public String getUri() {
        return "/contactus";
        
    }

    @Override
    public String getPageTitle() {
        return "팀코스트코몰";
    }

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/contact_us.css");
    }
}
