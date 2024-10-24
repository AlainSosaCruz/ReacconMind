package com.reacconmind.reacconmind.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class ImageService {

    // Método existente para subir imágenes
    public String upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
            File file = this.convertToFile(multipartFile, fileName);
            String URL = this.uploadFile(file, "imagesPublication/" + fileName);
            file.delete();
            return URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "The image could not be uploaded";
        }
    }

    // Método para eliminar la imagen anterior de Firebase
    public void deleteFromFirebase(String imageUrl) {
        try {
            // Extrae el nombre del archivo desde la URL
            String fileName = extractFileNameFromUrl(imageUrl);

            // Obtiene la referencia del archivo en Firebase
            BlobId blobId = BlobId.of("servicesmultimedia-38681.appspot.com", "imagesPublication/" + fileName);
            InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream("firebase-private-key.json");
            Credentials credentials = GoogleCredentials.fromStream(inputStream);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

            // Elimina el archivo
            storage.delete(blobId);
            System.out.println("Imagen eliminada exitosamente de Firebase");
        } catch (StorageException | IOException e) {
            System.out.println("Error al eliminar la imagen de Firebase: " + e.getMessage());
        }
    }

    // Método para extraer el nombre del archivo de la URL
    private String extractFileNameFromUrl(String imageUrl) {
        try {
            String decodedUrl = java.net.URLDecoder.decode(imageUrl, StandardCharsets.UTF_8.name());
            return decodedUrl.substring(decodedUrl.lastIndexOf("/") + 1, decodedUrl.indexOf("?alt=media"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Métodos existentes (convertToFile, getExtension, uploadFile)...
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String uploadFile(File file, String filePath) throws IOException {
        String extension = this.getExtension(filePath);
        String contentType = extension.equals(".png") ? "image/png" : "image/jpeg"; // Define el tipo de contenido según
        // la extensión

        BlobId blobId = BlobId.of("servicesmultimedia-38681.appspot.com", filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(contentType) // Usa el tipo de contenido correcto
                .build();
        InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream("firebase-private-key.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String downloadURL = "https://firebasestorage.googleapis.com/v0/b/servicesmultimedia-38681.appspot.com/o/%s?alt=media";
        return String.format(downloadURL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
    }
}