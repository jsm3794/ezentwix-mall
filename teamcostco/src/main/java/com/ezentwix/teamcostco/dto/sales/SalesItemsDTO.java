package com.ezentwix.teamcostco.dto.sales;

import lombok.Data;

@Data
public class SalesItemsDTO {
    private Long sales_item_id;
    private Long sales_id;
    private Long product_code;
    private Integer qty;
    private Integer unit_price;
    private Integer total_price;
}
