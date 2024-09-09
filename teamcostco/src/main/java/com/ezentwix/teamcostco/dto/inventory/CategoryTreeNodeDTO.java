package com.ezentwix.teamcostco.dto.inventory;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CategoryTreeNodeDTO {
    private String id;
    private String name;
    private String parentId;
    private List<CategoryTreeNodeDTO> children;

    public CategoryTreeNodeDTO(String id, String name, String parentId, List<CategoryTreeNodeDTO> children) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.children = children != null ? children : new ArrayList<>();
    }

}
