package com.academiadodesenvolvedor.ecommerce_api.usecases;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadUseCase {
    
    public String execute(String storageDir, String filename, MultipartFile file) throws IOException {
        String extension = getExtension(file);
        String name = filename.concat(extension);
        Path storage = Paths.get("storage", storageDir);
        Path fileResolved = storage.resolve(name);
        Files.createDirectories(storage);

        Files.copy(file.getInputStream(), fileResolved);
        return fileResolved.toString();
    }

    private String getExtension(MultipartFile file) {
        String extension = "";
        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        return extension;
    }

    public String execute(String filename, MultipartFile file) throws IOException {
        return this.execute("upload/", filename, file);
    }

    public String execute(MultipartFile file) throws IOException {
        return this.execute(UUID.randomUUID().toString(), file);
    }
}
