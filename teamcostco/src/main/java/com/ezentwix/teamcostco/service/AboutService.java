package com.ezentwix.teamcostco.service;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AboutService implements PageMetadataProvider {

    @Override
    public String getUri() {
        return "/aboutus";
    }

    @Override
    public String getPageTitle() {
        return "팀코스트코몰";
    }
    
}
