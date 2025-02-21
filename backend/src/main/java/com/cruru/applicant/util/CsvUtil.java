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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CsvUtil {

    private static final String CSV_HEADER_BASE = "이름,이메일,전화번호,제출일시";
    private static final byte[] UTF8_BOM = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
                .append(quoteCsvField(formatPhoneNumber(line.phone()))).append(",")
                .append(quoteCsvField(line.submissionDate().format(DATE_TIME_FORMATTER)));

        for (String answer : line.answers()) {
            lineBuilder.append(",").append(quoteCsvField(answer));
        }
        return lineBuilder.toString();
    }

    private static String formatPhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) {
            return "";
        }

        String onlyDigits = phone.replaceAll("[^0-9]", "");

        if (onlyDigits.length() == 11 && onlyDigits.startsWith("01")) {
            return onlyDigits.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
        }

        if (onlyDigits.length() == 10) {
            return onlyDigits.replaceFirst("(\\d{2,3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
        }

        if (Pattern.matches(".*-.*", phone)) {
            return phone;
        }

        return phone;
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
