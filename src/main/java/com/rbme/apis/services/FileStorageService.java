package com.rbme.apis.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String uploadFile(MultipartFile file, String folder);

    void deleteFile(String filePath);

}