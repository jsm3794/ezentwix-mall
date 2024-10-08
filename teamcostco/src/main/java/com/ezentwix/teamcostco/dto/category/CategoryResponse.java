package com.ezentwix.teamcostco.dto.category;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CategoryResponse {
    @JsonProperty("categories")
    private List<CategoryDTO> categories;
}
