package com.ezentwix.teamcostco.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezentwix.teamcostco.dto.cart.CartDTO;
import com.ezentwix.teamcostco.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartRestController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartDTO cartDTO) {
        boolean result = cartService.addToCart(cartDTO);

        if (result) {
            return ResponseEntity.ok("상품이 장바구니에 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니 추가에 실패했습니다.");
        }
    }

    @GetMapping("/checked/{product_code}/{checked}")
    public ResponseEntity<String> updateCheckedStatus(
            @PathVariable("product_code") Long productCode,
            @PathVariable("checked") String checked) {

        boolean result = cartService.updateCheckedStatus(productCode, checked);

        if (result) {
            return ResponseEntity.ok("체크 상태가 수정되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("체크 상태 수정에 실패했습니다.");
        }
    }

    @GetMapping("/quantity/{product_code}/{product_count}")
    public ResponseEntity<String> updateProductCount(
            @PathVariable("product_code") Long productCode,
            @PathVariable("product_count") Long productCount) {

        boolean result = cartService.updateProductCount(productCode, productCount);

        if (result) {
            return ResponseEntity.ok("수량이 수정되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("수량 수정에 실패했습니다.");
        }
    }

    @DeleteMapping("/delete/{cart_id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable("cart_id") Long cartId) {
        boolean result = cartService.deleteCartItem(cartId);

        if (result) {
            return ResponseEntity.ok("장바구니 아이템이 삭제되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("장바구니 아이템 삭제에 실패했습니다.");
        }
    }
}
