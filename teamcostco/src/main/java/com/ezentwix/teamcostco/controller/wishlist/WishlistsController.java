package com.ezentwix.teamcostco.controller.wishlist;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezentwix.teamcostco.dto.WishlistsDTO;
import com.ezentwix.teamcostco.service.AuthService;
import com.ezentwix.teamcostco.service.WishlistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wishlist")
public class WishlistsController {
    private final WishlistService wishlistService;
    private final AuthService authService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addWishlist(@RequestParam Long product_code) {
        Map<String, Object> response = new HashMap<>();
        boolean result;
        try {
            WishlistsDTO wishlistsDTO = new WishlistsDTO();
            wishlistsDTO.setProduct_code(product_code);
            wishlistsDTO.setSocial_id(authService.getSocialIdFromSession());
            result = wishlistService.addWishlist(wishlistsDTO);
            response.put("success", result);
            response.put("message", "위시리스트 성공적으로 추가되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "위시리스트 추가 중 오류가 발생했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteWishlist(@RequestParam Long product_code) {
        Map<String, Object> response = new HashMap<>();
        boolean result;
        try {
            WishlistsDTO wishlistsDTO = new WishlistsDTO();
            wishlistsDTO.setProduct_code(product_code);
            wishlistsDTO.setSocial_id(authService.getSocialIdFromSession());
            result = wishlistService.deleteWishlist(wishlistsDTO);
            response.put("success", result);
            response.put("message", "위시리스트에서 성공적으로 삭제되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "위시리스트 삭제 중 오류가 발생했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }
}


