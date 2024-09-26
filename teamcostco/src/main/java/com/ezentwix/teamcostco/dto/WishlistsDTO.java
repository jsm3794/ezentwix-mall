package com.ezentwix.teamcostco.dto;

import lombok.Data;


@Data
public class WishlistsDTO {
    private Integer wishlist_id;
    private String social_id;
    private Long product_code;
    private String product_name;

    private Integer selling_price;
    private String brand;
    private Double discount;

    public Integer getDiscountedPrice() {
        return (int) Math.floor(this.selling_price * (1 - this.discount)) / 10 * 10;
    }
}
