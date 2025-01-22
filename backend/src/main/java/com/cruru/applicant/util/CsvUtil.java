package com.cruru.applicant.util;

import com.cruru.applicant.domain.dto.ApplicantCsvLine;
import com.cruru.applicant.exception.CsvWriteException;
import com.cruru.question.domain.Question;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CsvUtil {

    private static final String CSV_HEADER_BASE = "이름,이메일,전화번호,제출일시";
    private static final byte[] UTF8_BOM = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    public static ByteArrayInputStream writeToCsv(
            List<ApplicantCsvLine> data,
            List<Question> questions
    ) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {

            outputStream.write(UTF8_BOM);

            writer.write(createHeaderLine(questions));
            writer.newLine();

            for (ApplicantCsvLine line : data) {
                writer.write(createDataLine(line));
                writer.newLine();
            }
            writer.flush();

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new CsvWriteException();
        }
    }

    private static String createHeaderLine(List<Question> questions) {
        StringBuilder headerBuilder = new StringBuilder(CSV_HEADER_BASE);
        for (Question q : questions) {
            headerBuilder.append(",").append(quoteCsvField(q.getContent()));
        }
        return headerBuilder.toString();
    }

    private static String createDataLine(ApplicantCsvLine line) {
        StringBuilder lineBuilder = new StringBuilder()
                .append(quoteCsvField(line.name())).append(",")
                .append(quoteCsvField(line.email())).append(",")
                .append(quoteCsvField(line.phone())).append(",")
                .append(quoteCsvField(line.submissionDate().toString()));

        for (String answer : line.answers()) {
            lineBuilder.append(",").append(quoteCsvField(answer));
        }
        return lineBuilder.toString();
    }

    private static String quoteCsvField(String field) {
        if (field == null) {
            return "";
        }
        String escaped = field.replace("\"", "\"\"");

        if (escaped.contains(",") || escaped.contains("\"")
                || escaped.contains("\n") || escaped.contains("\r")) {
            escaped = "\"" + escaped + "\"";
        }
        return escaped;
    }
}
