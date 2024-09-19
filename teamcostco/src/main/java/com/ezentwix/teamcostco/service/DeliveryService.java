package com.ezentwix.teamcostco.service;

import com.ezentwix.teamcostco.PageMetadataProvider;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class DeliveryService implements PageMetadataProvider {

    @Override
    public String getUri() {
        return "/customer/delivery_management";
    }

    @Override
    public String getPageTitle() {
        return "배송지 관리";
    }

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/delivery_management.css");
    }

    @Override
    public List<String> getJsFiles() {
        return null; // 필요한 경우 JS 파일 경로를 추가합니다.
    }

}
