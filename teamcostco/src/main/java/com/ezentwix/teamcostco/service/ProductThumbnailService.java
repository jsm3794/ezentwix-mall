package com.ezentwix.teamcostco.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ProductThumbnailService {

    private static final Logger logger = LoggerFactory.getLogger(ProductThumbnailService.class);

    @Value("${upload.path:/uploads/product_thumbnails}")
    private String uploadPath;

    public String getUploadPath() {
        return uploadPath;
    }

    public boolean uploadThumbnail(String thumbnailUrl, String productCode, boolean overwrite) {
        try {
            // Create the directory if it doesn't exist
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Define the file path
            String filename = productCode + ".jpg"; // Assuming thumbnails are in jpg format
            File file = new File(uploadDir, filename);

            // Check if the file already exists
            if (file.exists() && !overwrite) {
                logger.warn("File already exists and overwrite is not allowed: {}", file.getAbsolutePath());
                return false;
            }

            // Open the connection to the URL
            URL url = new URL(thumbnailUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the content type of the file
            String contentType = connection.getContentType();

            // Check if the content type is an image
            if (contentType == null || !contentType.startsWith("image/")) {
                logger.error("Invalid content type: {}", contentType);
                return false;
            }

            // Download the image if the content type is valid
            try (InputStream inputStream = connection.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream(file)) {

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                logger.info("Thumbnail uploaded successfully: {}", file.getAbsolutePath());
                return true;
            } catch (IOException e) {
                logger.error("Error saving file: {}", e.getMessage());
                return false;
            }
        } catch (IOException e) {
            logger.error("Error downloading thumbnail: {}", e.getMessage());
            return false;
        }
    }

    public Resource getThumbnail(String productCode) {
        String filename = productCode + ".jpg"; // Assuming thumbnails are in jpg format
        File file = new File(uploadPath, filename);

        if (file.exists()) {
            try {
                return new FileSystemResource(file);
            } catch (Exception e) {
                logger.error("Error retrieving file: {}", e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }
}
