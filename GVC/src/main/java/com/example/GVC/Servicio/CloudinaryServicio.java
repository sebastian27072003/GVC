package com.example.GVC.Servicio;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServicio {


    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServicio(@Value("${cloudinary.cloudName}") String cloudName,
                              @Value("${cloudinary.apiKey}") String apiKey,
                              @Value("${cloudinary.apiSecret}") String apiSecret) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }



    public String uploadImage(MultipartFile file) throws IOException {
        File localFile = convertMultipartFileToFile(file);
        // Verifica si el archivo se convirtió correctamente
        if (localFile == null || !localFile.exists()) {
            throw new FileNotFoundException("El archivo no existe o no se creó correctamente: " + localFile.getAbsolutePath());
        }
        // Llamada a Cloudinary para subir el archivo
        try {
            Map uploadResult = cloudinary.uploader().upload(localFile, ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Error al subir el archivo a Cloudinary", e);
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // Crea un archivo temporal en una ruta controlada
        File tempFile = new File(System.getProperty("java.io.tmpdir"), multipartFile.getOriginalFilename());
        if (!multipartFile.isEmpty()) {
            // Transferir el contenido del MultipartFile al archivo temporal
            multipartFile.transferTo(tempFile);
        } else {
            throw new FileNotFoundException("El archivo recibido está vacío.");
        }
        return tempFile;
    }
}
