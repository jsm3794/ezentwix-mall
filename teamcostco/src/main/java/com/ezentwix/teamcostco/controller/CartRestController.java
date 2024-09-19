package com.ezentwix.teamcostco.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/add")
    public ResponseEntity<String> addToCart(
            @ModelAttribute CartDTO cartDTO) {
        boolean result = cartService.addToCart(cartDTO);

        System.out.println(cartDTO);

        if (result) {
            return ResponseEntity.ok("장바구니에 추가되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("장바구니 추가에 실패했습니다.");
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
}
