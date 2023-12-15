package com.example.springwebtest.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {


    @GetMapping("/{fileName}")
    public ResponseEntity<?> download(@PathVariable String fileName) throws IOException {
        if (!StringUtils.hasText(fileName)) {
            return ResponseEntity.badRequest().body("File name is empty");
        }

        File file = new File("product_images/" + fileName);

        HttpHeaders headers = new HttpHeaders();
        List<String> customHeaders = new ArrayList<>();
        customHeaders.add(HttpHeaders.CONTENT_DISPOSITION);
        customHeaders.add("Content-Response");
        headers.setAccessControlExposeHeaders(customHeaders);
        headers.set("Content-Disposition", "attachment;filename=" + file.getName());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        byte[] imageData = Files.readAllBytes(file.toPath());
        if (ObjectUtils.isEmpty(imageData)) {
            return ResponseEntity.noContent().build();
        }
        ByteArrayResource resource = new ByteArrayResource(imageData);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
