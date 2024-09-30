package com.ezentwix.teamcostco.controller.product;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ezentwix.teamcostco.dto.product.ProductThumbnailDTO;
import com.ezentwix.teamcostco.service.ThumbnailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ThumbnailController {

    private final ThumbnailService thumbnailService;

    @PostMapping("/upload_product_thumbnail")
    public ResponseEntity<String> uploadThumbnail(@RequestParam("url") MultipartFile file,
            @RequestParam("product_code") Long productCode) {
        try {
            ProductThumbnailDTO dto = new ProductThumbnailDTO();
            dto.setProduct_image_file(file);
            dto.setProduct_code(productCode);
            dto.setCreate_date(LocalDateTime.now());
            dto.setUpdate_date(LocalDateTime.now());
            thumbnailService.upload(dto);

            return ResponseEntity.ok("파일 업로드 성공");
        } catch (IOException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패: " + e.getMessage());
        }
    }

    @GetMapping("/product_thumbnail/{productCode}")
    public ResponseEntity<Void> getThumbnail(
            @PathVariable("productCode") Long productCode) {

        String thumbnailUrl = thumbnailService.getThumbnailUrl(productCode).getThumbnail_url();

        if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(thumbnailUrl));
        return ResponseEntity.status(302).headers(headers).build();
    }

}
