package com.rbme.apis.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.Arrays;

@Configuration
public class FileResourceConfig implements WebMvcConfigurer {

    @Value("${app.upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/product/images/**")
                .addResourceLocations(
                        "file:" + uploadPath + "/product/images/"
                );

        registry.addResourceHandler("/company/banner/**")
                .addResourceLocations(
                        "file:" + uploadPath + "/company/banner/"
                );

        // Company logo
        registry.addResourceHandler("/company/logo/**")
                .addResourceLocations(
                        "file:" + uploadPath + "/company/logo/"
                );
    }

    @PostConstruct
    public void checkUploadDirectory() {
        System.out.println("=================================");
        System.out.println("UPLOAD PATH: " + uploadPath);

        File directory = new File(uploadPath);

        System.out.println("DIRECTORY EXISTS: " + directory.exists());
        System.out.println("DIRECTORY: " + directory.getAbsolutePath());

        File imageDirectory =
                new File(uploadPath + "/product/images");

        System.out.println(
                "IMAGE DIRECTORY EXISTS: " + imageDirectory.exists()
        );

        if (imageDirectory.exists()) {
            System.out.println(
                    "IMAGE FILES: "
                            + Arrays.toString(imageDirectory.list())
            );
        }

        System.out.println("=================================");
    }
}