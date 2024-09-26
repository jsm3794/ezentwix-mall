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

    public boolean updateProductCount(Long cart_id, Long productCount) {
        Map<String, Object> params = new HashMap<>();
        params.put("cart_id", cart_id);
        params.put("product_count", productCount);
        int result = sql.update("Cart.updateProductCount", params);
        return result > 0;
    }

    public CartDTO getByProductCode(Long productCode, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("product_code", productCode);
        params.put("social_id", customerId);
        return sql.selectOne("Cart.getByProductCode", params);
    }

    public List<CartDTO> getCartsWithProducts(String customer_id) {
        List<CartDTO> list = sql.selectList("Cart.getCartsWithProducts", customer_id);
        return list;
    }

    // checked 상태를 업데이트하는 메서드 추가
    public boolean updateCheckedStatus(Long cartId, String checked) {
        Map<String, Object> params = new HashMap<>();
        params.put("cart_id", cartId);
        params.put("checked", checked);
        int result = sql.update("Cart.updateCheckedStatus", params);
        return result > 0;
    }

    public boolean deleteCartItem(Long cartId) {
        int result = sql.delete("Cart.deleteCartItem", cartId);
        return result > 0;
    }

    public boolean deleteCartByProductCode(Map<String, Object> data) {
        int result = sql.delete("Cart.deleteCartByProductCode", data);
        return result > 0;
    }

    public CartDTO getCartItemById(Long cartId) {
        return sql.selectOne("Cart.getCartItemById", cartId);
    }
}
