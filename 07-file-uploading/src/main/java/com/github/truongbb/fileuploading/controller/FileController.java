package com.github.truongbb.fileuploading.controller;


import com.github.truongbb.fileuploading.model.response.UploadFileResponse;
import com.github.truongbb.fileuploading.service.FileService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileController {

    FileService fileService;

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        return fileService.upload(multipartFile);
    }

    @PostMapping("/multiple")
    public UploadFileResponse uploadMultiple(@RequestParam("files") List<MultipartFile> multipartFile) {
        return fileService.upload(multipartFile);
    }

    @GetMapping("/{fileName}")
    public String download(@PathVariable String fileName) throws IOException {
        return fileService.download(fileName);
    }

}
