package com.ezentwix.teamcostco.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezentwix.teamcostco.dto.inventory.CategoryTreeNodeDTO;
import com.ezentwix.teamcostco.dto.inventory.DetailCategoryDTO;
import com.ezentwix.teamcostco.dto.inventory.LargeCategoryDTO;
import com.ezentwix.teamcostco.dto.inventory.MediumCategoryDTO;
import com.ezentwix.teamcostco.dto.inventory.SmallCategoryDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexService {
    private final CategoryService categoryService;

    public void setCategoryModel(Model model) {
        // Fetch categories
        List<LargeCategoryDTO> largeCategories = categoryService.getLargeList();
        List<MediumCategoryDTO> mediumCategories = categoryService.getMediumList(null);
        List<SmallCategoryDTO> smallCategories = categoryService.getSmallList(null);
        List<DetailCategoryDTO> detailCategories = categoryService.getDetailList(null);


        // Create maps for quick lookup
        Map<String, List<MediumCategoryDTO>> mediumCategoryMap = mapByParentId(mediumCategories, MediumCategoryDTO::getLarge_id);
        Map<String, List<SmallCategoryDTO>> smallCategoryMap = mapByParentId(smallCategories, SmallCategoryDTO::getMedium_id);
        Map<String, List<DetailCategoryDTO>> detailCategoryMap = mapByParentId(detailCategories, DetailCategoryDTO::getSmall_id);

        // Build category tree
        List<CategoryTreeNodeDTO> categoryTree = buildCategoryTree(largeCategories, mediumCategoryMap, smallCategoryMap, detailCategoryMap);

        // Add the constructed category tree to the model
        model.addAttribute("categoryNode", categoryTree);
    }

    private <T> Map<String, List<T>> mapByParentId(List<T> items, Function<T, String> parentIdExtractor) {
        Map<String, List<T>> map = new HashMap<>();
        for (T item : items) {
            String parentId = parentIdExtractor.apply(item);
            map.computeIfAbsent(parentId, k -> new ArrayList<>()).add(item);
        }
        return map;
    }

    private List<CategoryTreeNodeDTO> buildCategoryTree(
            List<LargeCategoryDTO> largeCategories,
            Map<String, List<MediumCategoryDTO>> mediumCategoryMap,
            Map<String, List<SmallCategoryDTO>> smallCategoryMap,
            Map<String, List<DetailCategoryDTO>> detailCategoryMap) {

        List<CategoryTreeNodeDTO> categoryNodes = new ArrayList<>();
        
        for (LargeCategoryDTO large : largeCategories) {
            CategoryTreeNodeDTO largeNode = createNode(large.getLarge_id(), large.getLarge_name(), null);
            List<MediumCategoryDTO> mediums = mediumCategoryMap.get(large.getLarge_id());

            if (mediums != null) {
                for (MediumCategoryDTO medium : mediums) {
                    CategoryTreeNodeDTO mediumNode = createNode(medium.getMedium_id(), medium.getMedium_name(), large.getLarge_id());
                    List<SmallCategoryDTO> smalls = smallCategoryMap.get(medium.getMedium_id());

                    if (smalls != null) {
                        for (SmallCategoryDTO small : smalls) {
                            CategoryTreeNodeDTO smallNode = createNode(small.getSmall_id(), small.getSmall_name(), medium.getMedium_id());
                            List<DetailCategoryDTO> details = detailCategoryMap.get(small.getSmall_id());

                            if (details != null) {
                                for (DetailCategoryDTO detail : details) {
                                    CategoryTreeNodeDTO detailNode = createNode(detail.getDetail_id(), detail.getDetail_name(), small.getSmall_id());
                                    smallNode.getChildren().add(detailNode);
                                    if(small.getSmall_id().equals("100009279")){
                                        System.out.println(detailNode);
                                    }
                                }
                            }

                            mediumNode.getChildren().add(smallNode);
                        }
                    }

                    largeNode.getChildren().add(mediumNode);
                }
            }

            categoryNodes.add(largeNode);
        }
        
        return categoryNodes;
    }

    private CategoryTreeNodeDTO createNode(String id, String name, String parentId) {
        return new CategoryTreeNodeDTO(id, name, parentId, new ArrayList<>());
    }
}
