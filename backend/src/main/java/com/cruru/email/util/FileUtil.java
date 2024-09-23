package com.cruru.email.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class FileUtil {

    private static final String FILE_PREFIX = UUID.randomUUID() + "_";
    private static final String FILE_SUFFIX = "_";

    private FileUtil() {
    }

    public static List<File> saveTempFiles(List<MultipartFile> files) throws IOException {
        if (files == null) {
            return new ArrayList<>();
        }
        List<File> tempFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            File tempFile = File.createTempFile(FILE_PREFIX, FILE_SUFFIX + file.getOriginalFilename());
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
            return;
        }
        log.info("삭제할 파일이 존재하지 않습니다: {}", file.getAbsolutePath());
    }
}
