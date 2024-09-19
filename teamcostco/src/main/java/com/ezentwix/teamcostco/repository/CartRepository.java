package com.ezentwix.teamcostco.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezentwix.teamcostco.dto.cart.CartDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CartRepository {
    private final SqlSessionTemplate sql;

    public boolean addToCart(CartDTO cartDTO) {
        int result = sql.insert("Cart.addToCart", cartDTO);
        return result > 0; // 1 이상의 결과가 반환되면 성공
    }

    public boolean updateProductCount(Long productCode, Long productCount) {
        Map<String, Object> params = new HashMap<>();
        params.put("product_code", productCode);
        params.put("product_count", productCount);
        int result = sql.update("Cart.updateProductCount", params);
        return result > 0;
    }

    public CartDTO getByProductCode(Long productCode, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("product_code", productCode);
        params.put("customer_id", customerId);
        return sql.selectOne("Cart.getByProductCode", params);
    }

    public List<CartDTO> getCartsWithProducts(String customer_id){
        List<CartDTO> list = sql.selectList("Cart.getCartsWithProducts", customer_id);
        System.out.println(list);
        return list;
    }
}
