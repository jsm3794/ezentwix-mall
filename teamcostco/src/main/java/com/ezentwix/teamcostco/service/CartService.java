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

    public List<CartDTO> getAll() {
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
        String userId = customerService.getUserIdFromSession();

        if (userId != null) {
            // 해당 사용자와 상품 코드로 장바구니 아이템 조회
            CartDTO cartItem = cartRepository.getByProductCode(productCode, userId);
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
        String userId = customerService.getUserIdFromSession();

        if (userId != null) {
            // 입력된 checked 값 검증 ('Y' 또는 'N'인지 확인)
            if (!"Y".equalsIgnoreCase(checked) && !"N".equalsIgnoreCase(checked)) {
                return false; // 유효하지 않은 값이면 실패 처리
            }

            // 해당 사용자와 상품 코드로 장바구니 아이템 조회
            CartDTO cartItem = cartRepository.getByProductCode(productCode, userId);

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

    public boolean deleteCartItem(Long cartId) {
        String userId = customerService.getUserIdFromSession();

        if (userId != null) {
            // 해당 cartId와 userId로 장바구니 아이템을 조회하여 확인
            CartDTO cartItem = cartRepository.getCartItemById(cartId);

            if (cartItem != null && cartItem.getCustomer_id().equals(userId)) {
                // 아이템 삭제
                return cartRepository.deleteCartItem(cartId);
            }
        }
        return false;
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

    @Override
    public List<String> getJsFiles() {
        return List.of("/js/contents/cart_list.js");

    }
}
