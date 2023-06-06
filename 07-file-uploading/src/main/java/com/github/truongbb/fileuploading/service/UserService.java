package com.github.truongbb.fileuploading.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UserService {

    private static final String LOCAL_FOLDER = "/Users/buibatruong/Desktop";

    public String uploadLocalFile(MultipartFile file) throws IOException {
        if (ObjectUtils.isEmpty(file) || file.isEmpty()) {
            return null;
        }
        String filePath = LOCAL_FOLDER + File.separator + file.getOriginalFilename();
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return filePath;
    }

}
