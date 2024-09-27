package com.ezentwix.teamcostco.service;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactUsService implements PageMetadataProvider {

    @Override
    public String getUri() {
        return "/contactus";
        
    }

    @Override
    public String getPageTitle() {
        return "팀코스트코몰";

    }
}
