package com.cruru.email.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class FileUtilTest {

    @DisplayName("임시 파일들을 임시 경로에 저장한다.")
    @Test
    void saveTempFiles() throws IOException {
        // given
        File file = new File(getClass().getClassLoader().getResource("static/email_test.txt").getFile());
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", input);

        // when
        List<File> tempFiles = FileUtil.saveTempFiles(List.of(multipartFile));

        // then
        assertAll(
                () -> assertThat(tempFiles).hasSize(1),
                () -> assertThat(tempFiles.get(0)).isFile(),
                () -> assertThat(tempFiles.get(0).getName()).endsWith("email_test.txt")
        );
    }

    @DisplayName("인자로 들어온 파일 컬렉션이 null이거나 empty인 경우 빈 리스트를 반환한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void saveTempFiles_nullOrEmpty(List<MultipartFile> files) throws IOException {
        // when
        List<File> tempFiles = FileUtil.saveTempFiles(files);

        // then
        assertThat(tempFiles).isEmpty();
    }

    @DisplayName("파일을 삭제한다.")
    @Test
    void deleteFile() throws IOException {
        // given
        File file = new File(getClass().getClassLoader().getResource("static/email_test.txt").getFile());
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", input);
        List<File> tempFiles = FileUtil.saveTempFiles(List.of(multipartFile));

        // when
        FileUtil.deleteFiles(tempFiles);

        // then
        assertThat(tempFiles.get(0)).doesNotExist();
    }
}
