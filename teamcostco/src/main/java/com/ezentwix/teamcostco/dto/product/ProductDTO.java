package com.ezentwix.teamcostco.dto.product;



import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductDTO {
    private Long product_id;
    private String product_name;
    private Long product_code;
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
    private LocalDateTime create_date;
    private LocalDateTime update_date;

    public Integer getDiscountedPrice() {
        return (int) Math.floor(this.selling_price * (1 - this.discount));
    }

    public Integer getTotal_qty() {
        return this.storage_qty + this.display_qty;
    }

}
