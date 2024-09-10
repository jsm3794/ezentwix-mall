package com.ezentwix.teamcostco.dto.filter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class ProductFilterDTO {
    int product_code;
    int selling_price_start;
    int selling_price_end;
    String brands;

    public void setBrands(String brands){
        this.brands = brands;
    }

    public List<String> getBrands() {
        if (brands == null || brands.isEmpty()) {
            return null;
        }
        return Arrays.stream(brands.split("\\|"))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
