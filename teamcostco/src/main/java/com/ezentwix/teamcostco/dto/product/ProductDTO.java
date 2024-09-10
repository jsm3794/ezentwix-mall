package com.ezentwix.teamcostco.dto.product;

import java.util.Date;

import lombok.Data;

@Data
public class ProductDTO {
    private Integer product_id;
    private String product_name;
    private Integer product_code;
    private String category_large;
    private String category_medium;
    private String category_small;
    private String category_detail;
    private Integer purchase_price;
    private Integer selling_price;
    private Double discount;
    private String brand;
    private Integer min_required_qty;
    private Integer storage_qty;
    private Integer display_qty;
    private Integer total_qty;
    private Date create_date;
    private Date update_date;
}
