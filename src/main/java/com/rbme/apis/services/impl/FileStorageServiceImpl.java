package com.rbme.apis.services.impl;

import com.rbme.apis.exception.BadRequestException;
import com.rbme.apis.services.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${app.upload.path}")
    private String uploadPath;

    @Override
    public String uploadFile(MultipartFile file, String folder) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        try {

            Path directory = Paths.get(uploadPath, folder);

            Files.createDirectories(directory);

            String originalFilename = StringUtils.cleanPath(
                    Objects.requireNonNull(file.getOriginalFilename()));

            String extension = "";

            int index = originalFilename.lastIndexOf(".");

            if (index > 0) {
                extension = originalFilename.substring(index);
            }

            String fileName = UUID.randomUUID() + extension;

            Path destination = directory.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return folder + "/" + fileName;

        } catch (IOException ex) {
            throw new BadRequestException("Unable to upload file.");
        }

    }

    @Override
    public void deleteFile(String filePath) {

        if (filePath == null || filePath.isBlank()) {
            return;
        }

        try {

            Path path = Paths.get(uploadPath, filePath);

            Files.deleteIfExists(path);

        } catch (IOException ignored) {

        }

    }

}