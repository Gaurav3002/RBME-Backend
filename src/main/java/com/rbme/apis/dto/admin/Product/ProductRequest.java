package com.rbme.apis.dto.admin.Product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductRequest {

    private Long companyId;

    private Long categoryId;

    private Long productTypeId;

    private String modelNo;

    private String title;

    private String description;

    private String thumbnail;

    private String youtubeUrl;

    private Boolean featured;

    private Boolean active;

    private List<MultipartFile> images;
    private List<Long> deletedImageIds;
}