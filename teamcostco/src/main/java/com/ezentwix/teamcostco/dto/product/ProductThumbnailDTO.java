package com.ezentwix.teamcostco.dto.product;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductThumbnailDTO {
    private Long thumbnail_id;
    private Long product_code;
    private String thumbnail_url;
    private LocalDateTime create_date;
    private LocalDateTime update_date;
    
    private MultipartFile product_image_file; // 파일 업로드용
}
