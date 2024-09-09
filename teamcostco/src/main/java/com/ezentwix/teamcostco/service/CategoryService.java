package com.ezentwix.teamcostco.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.dto.inventory.DetailCategoryDTO;
import com.ezentwix.teamcostco.dto.inventory.SmallCategoryDTO;
import com.ezentwix.teamcostco.dto.inventory.LargeCategoryDTO;
import com.ezentwix.teamcostco.dto.inventory.MediumCategoryDTO;
import com.ezentwix.teamcostco.repository.CategoriesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoriesRepository categoriesRepository;

    public Map<String, Object> getLarge() {
        List<LargeCategoryDTO> list = categoriesRepository.getlarge();
        Map<String, Object> map = new HashMap<>();
        map.put("", "전체");
        list.forEach(item -> {
            map.put(item.getLarge_id(), item.getLarge_name());
        });
        return map;
    }

    public List<LargeCategoryDTO> getLargeList() {
        return categoriesRepository.getlarge();
    }

    public List<MediumCategoryDTO> getMediumList(Integer id) {
        return categoriesRepository.getMedium(id);
    }

    public List<SmallCategoryDTO> getSmallList(Integer id) {
        return categoriesRepository.getSmall(id);
    }

    public List<DetailCategoryDTO> getDetailList(Integer id) {
        return categoriesRepository.getDetail(id);
    }
}
