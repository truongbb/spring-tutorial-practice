package com.github.truongbb.fileuploading.service;

import com.github.truongbb.fileuploading.model.response.UploadFileResponse;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class FileService {

    @Value("${application.firebase.bucket-name}")
    private String bucketName;

    @Value("${application.firebase.download-url}")
    private String downloadUrl;

    @Value("${application.firebase.config-json}")
    private String configJson;

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(configJson));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(downloadUrl, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public UploadFileResponse upload(List<MultipartFile> multipartFile) {
        if (CollectionUtils.isEmpty(multipartFile)) {
            return null;
        }
        List<String> fileUrls = multipartFile.stream().map(this::upload).collect(Collectors.toList());
        return UploadFileResponse.builder().fileUrls(fileUrls).build();
    }

    public String upload(MultipartFile multipartFile) {
        if (ObjectUtils.isEmpty(multipartFile) || multipartFile.isEmpty()) {
            return null;
        }
        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            if (!StringUtils.hasText(fileName)) {
                return null;
            }
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.

            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String fileUrl = this.uploadFile(file, fileName);// to get uploaded file link
            file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
            return fileUrl;                                                              // Your customized response
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public String download(String fileName) throws IOException {
        String destFileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));     // to set random strinh for destination file name
        String destFilePath = "/Users/buibatruong/Desktop/" + destFileName;                                    // to set destination file path

        ////////////////////////////////   Download  ////////////////////////////////////////////////////////////////////////
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(configJson));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        blob.downloadTo(Paths.get(destFilePath));
        return destFilePath;
    }

}
