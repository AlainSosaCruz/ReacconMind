package com.reacconmind.reacconmind.service;

import com.reacconmind.reacconmind.model.Multimedia;
import com.reacconmind.reacconmind.model.MultimediaType;
import com.reacconmind.reacconmind.repository.MultimediaRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MultimediaService {

    @Autowired
    private MultimediaRepository multimediaRepository; // Repositorio para Multimedia

    // Método para subir archivos multimedia
    public String uploadFile(MultipartFile file) {
        try {
            // Obtener el nombre del archivo original y generar un nuevo nombre
            String fileName = file.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

            // Convertir MultipartFile a File
            File tempFile = this.convertToFile(file, fileName);

            // Determinar la carpeta según el tipo de archivo
            String folder = getFolderForFileType(fileName);

            // Subir el archivo a Firebase y obtener la URL
            String fileUrl = this.uploadFileToFirebase(tempFile, folder + "/" + fileName);
            tempFile.delete(); // Eliminar el archivo temporal

            // Guardar la URL y tipo de multimedia en la base de datos
            Multimedia multimedia = new Multimedia(); // Crear instancia de Multimedia
            multimedia.setUrl(fileUrl); // Establecer la URL obtenida de Firebase
            multimedia.setType(MultimediaType.fromFileName(fileName)); // Asignar el tipo

            multimediaRepository.save(multimedia); // Guarda el objeto Multimedia

            return fileUrl; // Retornar la URL del archivo
        } catch (Exception e) {
            e.printStackTrace();
            return "El archivo no pudo ser subido";
        }
    }

    // Método para determinar la carpeta según el tipo de archivo
    private String getFolderForFileType(String fileName) {
        String extension = this.getExtension(fileName).toLowerCase();
        switch (extension) {
            case ".png":
            case ".jpg":
            case ".jpeg":
            case ".gif":
                return "imagesMultimedia"; // Carpeta para imágenes
            case ".mp4":
            case ".avi":
            case ".mov":
            case ".mkv":
                return "videosMultimedia"; // Carpeta para videos
            case ".mp3":
            case ".wav":
            case ".aac":
                return "audiosMultimedia"; // Carpeta para audios
            default:
                return "others"; // Carpeta por defecto para otros tipos
        }
    }

    // Método para convertir MultipartFile a File
    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    // Método para obtener la extensión de un archivo
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    // Método para subir el archivo a Firebase y obtener la URL
    private String uploadFileToFirebase(File file, String filePath) throws IOException {
        // Configuración de Firebase Storage
        InputStream inputStream = MultimediaService.class.getClassLoader().getResourceAsStream("firebase-private-key.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        // Determinar el tipo de contenido
        String extension = this.getExtension(filePath);
        String contentType = extension.equals(".png") || extension.equals(".jpg") || extension.equals(".jpeg")
                ? "image/jpeg" : extension.equals(".mp4") ? "video/mp4" : extension.equals(".mp3") ? "audio/mpeg" : "application/octet-stream";

        // Subir el archivo a Firebase
        BlobId blobId = BlobId.of("servicesmultimedia-38681.appspot.com", filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(contentType) // Usa el tipo de contenido correcto
                .build();

        storage.create(blobInfo, Files.readAllBytes(file.toPath())); // Crea el blob en Firebase

        // Generar la URL de descarga
        String downloadURL = "https://firebasestorage.googleapis.com/v0/b/servicesmultimedia-38681.appspot.com/o/%s?alt=media";
        return String.format(downloadURL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
    }
}
