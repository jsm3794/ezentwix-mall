package com.ezentwix.teamcostco.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ezentwix.teamcostco.dto.product.ProductThumbnailDTO;
import com.ezentwix.teamcostco.repository.ThumbnailsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ThumbnailService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final ThumbnailsRepository thumbnailsRepository;

    public void upload(ProductThumbnailDTO productThumbnailDTO) throws IOException {
        MultipartFile productImageFile = productThumbnailDTO.getProduct_image_file();

        if (productImageFile == null || productImageFile.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        }

        String fileName = productThumbnailDTO.getProduct_code() + ".jpg";

        // S3 버킷에 동일한 파일명이 존재하는지 확인
        if (amazonS3.doesObjectExist(bucketName, fileName)) {
            log.info("**** 이미 파일이 존재합니다. ****");
        } else {
            // 파일 업로드
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, productImageFile.getInputStream(), null));
        }

        // 업로드된 파일의 URL 가져오기
        String fileUrl = amazonS3.getUrl(bucketName, fileName).toString();

        productThumbnailDTO.setThumbnail_url(fileUrl);

        // DB에 삽입
        thumbnailsRepository.insertProductThumbnails(productThumbnailDTO);
    }

    public void uploadFromUrl(String url, Long product_code) throws IOException {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        String fileName = product_code + ".jpg";

        // S3 버킷에 동일한 파일명이 존재하는지 확인
        if (amazonS3.doesObjectExist(bucketName, fileName)) {
            log.info("**** 이미 파일이 존재합니다. 업로드를 중단합니다. ****");
            return; // 이미 존재하면 메서드 종료
        }

        // 이미지 다운로드
        InputStream inputStream = null;
        try {
            URL imageUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 연결 타임아웃 5초
            connection.setReadTimeout(5000); // 읽기 타임아웃 5초

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                log.error("Failed to download image from URL: {}. Response Code: {}", url, responseCode);
                throw new IOException("Failed to download image from URL: " + url);
            }

            inputStream = connection.getInputStream();

            // S3 버킷에 이미지 업로드
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, null);
            amazonS3.putObject(putObjectRequest);
            log.info("**** S3에 파일이 성공적으로 업로드되었습니다: {} ****", fileName);

            // 업로드된 파일의 URL 가져오기
            String fileUrl = amazonS3.getUrl(bucketName, fileName).toString();
            log.info("**** 업로드된 파일의 URL: {} ****", fileUrl);

            // DB에 저장
            ProductThumbnailDTO productThumbnailDTO = new ProductThumbnailDTO();
            productThumbnailDTO.setProduct_code(product_code);
            productThumbnailDTO.setThumbnail_url(fileUrl);
            thumbnailsRepository.insertProductThumbnails(productThumbnailDTO);
            log.info("**** DB에 썸네일 URL이 성공적으로 저장되었습니다: {} ****", fileUrl);

        } catch (IOException e) {
            log.error("이미지 다운로드 또는 업로드 중 오류가 발생했습니다: {}", e.getMessage());
            throw e; // 예외를 다시 던져 상위 계층에서 처리하도록 함
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.warn("InputStream을 닫는 중 오류가 발생했습니다: {}", e.getMessage());
                }
            }
        }
    }

    public ProductThumbnailDTO getThumbnailUrl(Long product_code) {
        return thumbnailsRepository.selectProductThumbnails(product_code);
    }
}
