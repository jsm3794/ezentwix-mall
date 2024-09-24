package com.ezentwix.teamcostco.controller.product;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezentwix.teamcostco.service.ProductThumbnailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductThumbnailRestController {

    private final ProductThumbnailService productThumbnailService;

    @GetMapping("/api/upload_product_thumbnail")
    public ResponseEntity<String> uploadThumbnail(
            @RequestParam(value = "url") String thumbnailUrl,
            @RequestParam(value = "product_code") String productCode,
            @RequestParam(value = "overwrite", defaultValue = "false") boolean overwrite) {

        boolean success = productThumbnailService.uploadThumbnail(thumbnailUrl, productCode, overwrite);

        if (success) {
            return ResponseEntity.ok("Thumbnail uploaded successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload thumbnail.");
        }
    }

    @GetMapping("/api/product_thumbnail/{productCode}")
    public ResponseEntity<Resource> getThumbnail(
            @PathVariable("productCode") String productCode) {

        Resource resource = productThumbnailService.getThumbnail(productCode);

        if (resource != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}
