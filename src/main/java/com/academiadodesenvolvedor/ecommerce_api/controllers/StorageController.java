package com.academiadodesenvolvedor.ecommerce_api.controllers;

import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tika.Tika;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController

@RequestMapping("/storage")
public class StorageController {

    @GetMapping("/**")
    public ResponseEntity<byte[]> getFiles(HttpServletRequest request) {
        try {
            String[] uri = request.getRequestURI().split("/");
            List<String> path = new ArrayList<>(Arrays.stream(uri).toList());
            path.removeIf(""::equals);
            String fileName = path.get(path.size() - 1);
            List<String> storagePath = path.subList(0, path.size() - 1);
            Path storage = Paths.get(String.join("/", storagePath));
            Path file = storage.resolve(fileName);

            if (Files.exists(file)) {
                byte[] fileBlob = Files.readAllBytes(file);
                String mimeType = getMimeType(fileBlob);
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
                        .body(fileBlob);
            }
            throw new HttpException("Não foi possível localizar este recurso", HttpStatus.NOT_FOUND);

        } catch (IOException e) {
            throw new HttpException("Não foi possível acessar esse recurso", HttpStatus.BAD_REQUEST);
        }
    }

    private String getMimeType(byte[] file) {
        Tika tika = new Tika();
        return tika.detect(file);
    }

}
