package com.ezentwix.teamcostco.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ezentwix.teamcostco.PageMetadataProvider;
import com.ezentwix.teamcostco.dto.cart.CartDTO;
import com.ezentwix.teamcostco.repository.CartRepository;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements PageMetadataProvider {
    private final CustomerService customerService;
    private final CartRepository cartRepository;

    public List<CartDTO> getAll(){
        return cartRepository.getCartsWithProducts(customerService.getUserIdFromSession());
    }

    public boolean addToCart(CartDTO cartDTO) {

        String userId = customerService.getUserIdFromSession();

        if (userId != null) {
            // DTO에 userId 설정 (customer_id에 매핑)
            cartDTO.setCustomer_id(userId);

            // 기존 상품을 찾기
            CartDTO existingCartItem = cartRepository.getByProductCode(cartDTO.getProduct_code(), userId);

            if (existingCartItem != null) {
                // 수량 업데이트
                existingCartItem.setProduct_count(existingCartItem.getProduct_count() + cartDTO.getProduct_count());
                return cartRepository.updateProductCount(existingCartItem.getCart_id(),
                        existingCartItem.getProduct_count());
            } else {
                // 장바구니에 추가
                return cartRepository.addToCart(cartDTO);
            }
        }

        return false; // userId가 없으면 추가 실패
    }

    public boolean updateProductCount(Long productCode, Long productCount) {
        // 수량 업데이트를 수행
        return cartRepository.updateProductCount(productCode, productCount);
    }

    @Override
    public String getUri() {
        return "/cart/cart_list";
    }

    @Override
    public String getPageTitle() {
        return "장바구니";
    }

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/cart_list.css");
    }
}
