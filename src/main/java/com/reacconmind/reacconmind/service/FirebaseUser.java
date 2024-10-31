package com.reacconmind.reacconmind.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class FirebaseUser {
    private static final String BUCKET_NAME = "reacconmind-e99ee.appspot.com";

    // Clase interna para almacenar las URLs
    public static class UploadResponse {
        private String originalUrl;
        private String thumbnailUrl;

        public UploadResponse(String originalUrl, String thumbnailUrl) {
            this.originalUrl = originalUrl;
            this.thumbnailUrl = thumbnailUrl;
        }

        public String getOriginalUrl() {
            return originalUrl;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }
    }

    public UploadResponse upload(MultipartFile multipartFile) {
        try {
            if (multipartFile.isEmpty()) {
                return null; // O lanza una excepción según tus necesidades
            }

            String originalFileName = generateUniqueFileName(multipartFile.getOriginalFilename());
            String thumbnailFileName = "thumb_" + originalFileName;

            File originalFile = convertToFile(multipartFile, originalFileName);
            File thumbnailFile = createThumbnail(originalFile, thumbnailFileName);

            String originalUrl = uploadFile(originalFile, originalFileName);
            String thumbnailUrl = uploadFile(thumbnailFile, thumbnailFileName);

            // Eliminar archivos temporales
            originalFile.delete();
            thumbnailFile.delete();

            return new UploadResponse(originalUrl, thumbnailUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // O lanza una excepción según tus necesidades
        } catch (Exception e) {
            e.printStackTrace();
            return null; // O lanza una excepción según tus necesidades
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = getExtension(originalFileName);
        return UUID.randomUUID().toString() + extension;
    }

    private String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return (lastIndex == -1) ? "" : fileName.substring(lastIndex);
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private File createThumbnail(File originalFile, String thumbnailFileName) throws IOException {
        File thumbnailFile = new File(thumbnailFileName);
        Thumbnails.of(originalFile)
                .size(150, 150)
                .toFile(thumbnailFile);
        return thumbnailFile;
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType("image/jpg")
                .build();

        try (InputStream inputStream = FirebaseUser.class.getClassLoader()
                .getResourceAsStream("firebase-privateAlain-key.json")) {
            if (inputStream == null) {
                throw new IOException("Failed to load Google Cloud credentials.");
            }

            Credentials credentials = GoogleCredentials.fromStream(inputStream);
            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(credentials)
                    .build()
                    .getService();

            storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error uploading file to Google Cloud Storage: " + e.getMessage());
        }

        String downloadURL = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME + "/o/%s?alt=media";
        return String.format(downloadURL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
}
