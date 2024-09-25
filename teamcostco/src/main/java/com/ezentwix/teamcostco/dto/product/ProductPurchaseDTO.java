package com.ezentwix.teamcostco.dto.product;

import lombok.Data;

@Data
public class ProductPurchaseDTO {
    private ProductDTO product;
    private Integer count;
}
