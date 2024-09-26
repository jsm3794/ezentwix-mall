package com.ezentwix.teamcostco.dto.cart;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CartDTO {
    private Long cart_id; // cart_id
    private String social_id; // customer_id
    private Long product_count; // product_count
    private Long product_code; // product_count
    private Character checked;
    private Integer product_id;
    private String product_name;
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
        if (this.storage_qty != null && this.display_qty != null) {
            return this.storage_qty + this.display_qty;
        }
        return 0;
    }

}
