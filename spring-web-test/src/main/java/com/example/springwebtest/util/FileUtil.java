package com.example.springwebtest.util;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class FileUtil<T> {
    private final Gson gson;

    public List<T> readDataFromFile(String filePath, Type targetClass) {
        if (!StringUtils.hasText(filePath)) {
            return null;
        }
        String fileDataStr = "";
        // java NIO
        File folder;
        try {
            folder = new File(filePath);
            if (folder.isFile()) {
                fileDataStr = new String(Files.readAllBytes(Paths.get(folder.getAbsolutePath())));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>(Arrays.asList(gson.fromJson(fileDataStr, targetClass)));
    }

    public void writeDataToFile(String filePath, List<T> data) {
        if (!StringUtils.hasText(filePath)) {
            return;
        }
        File folder = null;
        Writer writer = null;
        try {
            folder = new File(filePath);
            writer = new FileWriter(folder);
            gson.toJson(data, writer);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }



}
