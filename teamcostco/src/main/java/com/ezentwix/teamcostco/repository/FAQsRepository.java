package com.ezentwix.teamcostco.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezentwix.teamcostco.dto.faq.FAQsCategoryDTO;
import com.ezentwix.teamcostco.dto.faq.FAQsDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FAQsRepository {
    private final SqlSessionTemplate sql;

    public List<FAQsDTO> getAllFAQList() {
        return sql.selectList("FAQs.getAllFAQList");
    }

    public List<FAQsCategoryDTO> getAllCategoryList() {
        return sql.selectList("FAQs.getAllCategories");
    }
}
