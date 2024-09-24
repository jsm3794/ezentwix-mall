package com.ezentwix.teamcostco.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezentwix.teamcostco.dto.product.ProductBrandGroupDTO;
import com.ezentwix.teamcostco.dto.product.ProductDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final SqlSessionTemplate sql;

    public List<ProductDTO> getAll() {
        return sql.selectList("Products.getAll");
    }

    public Integer getTotalCategories() {
        return sql.selectOne("Products.getTotalCategories");
    }

    public Integer getTotalProductsQty() {
        return sql.selectOne("Products.getTotalProductsQty");
    }

    public Integer getLowProducts() {
        return sql.selectOne("Products.getLowProducts");
    }

    // 제품 ID로 제품을 가져오는 메서드 추가
    public ProductDTO findById(Integer productId) {
        return sql.selectOne("Products.findById", productId);
    }

    public void updateProduct(ProductDTO productDTO) {
        sql.update("Products.updateProduct", productDTO);
    }

    public List<ProductBrandGroupDTO> getBrandGroup(String product_name) {
        if (!product_name.trim().isEmpty()) {
            return sql.selectList("Products.getBrandGroupByProductName", product_name);
        }
        return null;
    }

    public ProductDTO getByProductCode(Long productCode){
        return sql.selectOne("Products.getByProductCode", productCode);
    }
}