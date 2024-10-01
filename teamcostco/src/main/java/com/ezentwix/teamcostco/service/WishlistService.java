package com.ezentwix.teamcostco.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;
import com.ezentwix.teamcostco.dto.WishlistsDTO;
import com.ezentwix.teamcostco.repository.WishlistsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WishlistService implements PageMetadataProvider {
    private final CustomerService customerService;
    private final WishlistsRepository wishlistsRepository;

    public boolean isWishProduct(Long product_code) {
        String socialId = customerService.getSocialIdFromSession();
        if (socialId == null) {
            return false;
        }

        Map<String, Object> data = Map.of("product_code", product_code, "social_id", socialId);

        return wishlistsRepository.isWishProduct(data);
    }

    public List<WishlistsDTO> getWiList(String social_id) {
        return wishlistsRepository.getWishlist(social_id);
    }

    public boolean addWishlist(WishlistsDTO wishlistsDTO) {
        if (!isWishProduct(wishlistsDTO.getProduct_code())) {
            return wishlistsRepository.addWishlist(wishlistsDTO);
        }
        return false;
    }

    public boolean deleteWishlist(WishlistsDTO wishlistsDTO) {
        if (isWishProduct(wishlistsDTO.getProduct_code())) {
            return wishlistsRepository.deleteWishlist(wishlistsDTO);
        }
        return false;

    }

    @Override
    public String getUri() {
        return "/customer/wishlist";
    }

    @Override
    public String getPageTitle() {
        return "위시리스트";
    }

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/wishlist.css");
    }

    @Override
    public List<String> getJsFiles() {
        return List.of("/js/contents/wishlist.js");
    }
}
