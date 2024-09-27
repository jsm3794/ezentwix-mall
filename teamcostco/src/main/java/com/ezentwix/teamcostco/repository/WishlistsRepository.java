package com.ezentwix.teamcostco.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezentwix.teamcostco.dto.WishlistsDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class WishlistsRepository {

    private final SqlSessionTemplate sql;

    public boolean addWishlist(WishlistsDTO wishlistsDTO) {
        int result = sql.insert("Wishlist.addWishlist", wishlistsDTO);
        return result > 0;
    }

    public boolean deleteWishlist(WishlistsDTO wishlistsDTO) {
        int result = sql.delete("Wishlist.deleteWishlist", wishlistsDTO);
       return result > 0;
    }

    public List<WishlistsDTO> getWishlist(String social_id) {
        return sql.selectList("Wishlist.getWishlist", social_id);
    }

    public Integer checkWishlist(WishlistsDTO wishlistsDTO) {
        return sql.selectOne("Wishlist.checkWishlist", wishlistsDTO);
    }

}
