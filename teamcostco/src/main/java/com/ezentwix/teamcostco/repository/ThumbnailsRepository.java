package com.ezentwix.teamcostco.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezentwix.teamcostco.dto.product.ProductThumbnailDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ThumbnailsRepository {
    private final SqlSessionTemplate sql;

    public void insertProductThumbnails(ProductThumbnailDTO productThumbnailDTO) {
        sql.insert("Thumbnails.insertProductThumbnail", productThumbnailDTO);
    }

    public ProductThumbnailDTO selectProductThumbnails(Long product_code){
        return sql.selectOne("Thumbnails.selectProductThumbnail", product_code);
    }
}
 