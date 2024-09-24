package com.ezentwix.teamcostco.service;

import java.io.Writer;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.ezentwix.teamcostco.PageMetadataProvider;
import com.ezentwix.teamcostco.dto.WishlistsDTO;
import com.ezentwix.teamcostco.repository.WishlistsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WishlistService implements PageMetadataProvider {

    private final WishlistsRepository wishlistsRepository;

    @Transactional(readOnly = true)
    public StreamingResponseBody streamWishlist(String social_id) {
        return outputStream -> {
            Writer writer = new java.io.OutputStreamWriter(outputStream);
            try {
                List<Integer> productCodes = wishlistsRepository.getWishlist(social_id);
                writer.write("[");
                for (int i = 0; i < productCodes.size(); i++) {
                    if (i > 0) {
                        writer.write(",");
                    }
                    writer.write(String.valueOf(productCodes.get(i)));
                    writer.flush();
                }
                writer.write("]");
            } finally {
                writer.close();
            }
        };
    }

    public void addWishlist(WishlistsDTO wishlistsDTO) {
        wishlistsRepository.addWishlist(wishlistsDTO);
    }

    public void deleteWishlist(WishlistsDTO wishlistsDTO) {
        wishlistsRepository.deleteWishlist(wishlistsDTO);
    }

    @Override
    public String getUri() {
        return "/wishlist/wishlist";
    }

    @Override
    public String getPageTitle() {
        return "찜목록";
    }

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/wishlist.css");
    }
}
