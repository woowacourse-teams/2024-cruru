package com.cruru.email.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    public static List<File> saveTempFiles(List<MultipartFile> files) throws IOException {
        if (files == null) {
            return new ArrayList<>();
        }
        List<File> tempFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            File tempFile = File.createTempFile("temp_", "_" + file.getOriginalFilename());
            file.transferTo(tempFile);
            tempFiles.add(tempFile);
        }
        return tempFiles;
    }

    public static void deleteFiles(List<File> files) {
        if (files != null) {
            files.forEach(FileUtil::deleteFile);
        }
    }

    private static void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }
}
