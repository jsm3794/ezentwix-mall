package com.ezentwix.teamcostco.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;
import com.ezentwix.teamcostco.dto.cart.CartDTO;
import com.ezentwix.teamcostco.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements PageMetadataProvider {
    private final CustomerService customerService;
    private final CartRepository cartRepository;

    public List<CartDTO> getAll() {
        return cartRepository.getCartsWithProducts(customerService.getSocialIdFromSession());
    }

    public boolean addToCart(CartDTO cartDTO) {

        String socialId = customerService.getSocialIdFromSession();

        if (socialId != null) {
            // DTO에 userId 설정 (customer_id에 매핑)
            cartDTO.setSocial_id(socialId);

            // 기존 상품을 찾기
            CartDTO existingCartItem = cartRepository.getByProductCode(cartDTO.getProduct_code(), socialId);

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
        String socialId = customerService.getSocialIdFromSession();

        if (socialId != null) {
            // 해당 사용자와 상품 코드로 장바구니 아이템 조회
            CartDTO cartItem = cartRepository.getByProductCode(productCode, socialId);
            if (cartItem != null) {
                // 수량 업데이트
                return cartRepository.updateProductCount(cartItem.getCart_id(), productCount);
            } else {
                // 장바구니 아이템을 찾을 수 없음
                return false;
            }
        }

        return false; // userId가 없으면 업데이트 실패
    }

    public boolean updateCheckedStatus(Long productCode, String checked) {
        String socialId = customerService.getSocialIdFromSession();

        if (socialId != null) {
            // 입력된 checked 값 검증 ('Y' 또는 'N'인지 확인)
            if (!"Y".equalsIgnoreCase(checked) && !"N".equalsIgnoreCase(checked)) {
                return false; // 유효하지 않은 값이면 실패 처리
            }

            // 해당 사용자와 상품 코드로 장바구니 아이템 조회
            CartDTO cartItem = cartRepository.getByProductCode(productCode, socialId);

            if (cartItem != null) {
                // checked 상태 업데이트
                cartItem.setChecked(checked.charAt(0)); // checked가 Character 타입인 경우
                return cartRepository.updateCheckedStatus(cartItem.getCart_id(), checked);
            } else {
                // 장바구니 아이템을 찾을 수 없음
                return false;
            }
        }

        return false; // userId가 없으면 실패 처리
    }

    public boolean deleteCartByProductCode(Long product_code){
        String socialId = customerService.getSocialIdFromSession();

        if (socialId != null) {
            cartRepository.deleteCartByProductCode(
                Map.of("product_code", product_code, "social_id", socialId));
        }
        return false;
    }

    public boolean deleteCartItem(Long cartId) {
        String socialId = customerService.getSocialIdFromSession();

        if (socialId != null) {
            // 해당 cartId와 userId로 장바구니 아이템을 조회하여 확인
            CartDTO cartItem = cartRepository.getCartItemById(cartId);

            if (cartItem != null && cartItem.getSocial_id().equals(socialId)) {
                // 아이템 삭제
                return cartRepository.deleteCartItem(cartId);
            }
        }
        return false;
    }

    @Override
    public String getUri() {
        return "/customer/cart_list";
    }

    @Override
    public String getPageTitle() {
        return "장바구니";
    }

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/cart_list.css");
    }

    @Override
    public List<String> getJsFiles() {
        return List.of("/js/contents/cart_list.js");

    }
}
