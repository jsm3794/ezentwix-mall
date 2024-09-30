package com.ezentwix.teamcostco.dto.product;

import lombok.Data;

@Data
public class RecommendProductDTO {

    private String product_name;
    private Long product_id; 
    private Long product_code;
    private String thumbnail_url;
    private Integer selling_price;

}
