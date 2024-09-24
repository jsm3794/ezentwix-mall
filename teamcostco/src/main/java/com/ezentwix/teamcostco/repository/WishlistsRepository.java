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

    public void addWishlist(WishlistsDTO wishlistsDTO) {
        sql.insert("Wishlist.addWishlist", wishlistsDTO);
        log.info("***** {} insert 완료 *****", wishlistsDTO);
    }

    public void deleteWishlist(WishlistsDTO wishlistsDTO) {
        sql.delete("Wishlist.deleteWishlist", wishlistsDTO);
        log.info("***** {} delete 완료 *****", wishlistsDTO);
    }

    public List<WishlistsDTO> getWishlist(String social_id) {
        return sql.selectList("Wishlist.getWishlist", social_id);
    }

}
