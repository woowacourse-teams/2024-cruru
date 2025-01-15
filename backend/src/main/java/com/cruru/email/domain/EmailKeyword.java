package com.cruru.email.domain;

import java.util.Map;
import java.util.Map.Entry;

public enum EmailKeyword {
    APPLICANT_NAME("{AP_NAME}"),
    CLUB_NAME("{CL_NAME}"),
    APPLY_FORM_TITLE("{RC_TITLE}"),
    PROCESS_NAME("{RC_STEP}"),
    ;

    private final String keyword;

    EmailKeyword(String keyword) {
        this.keyword = keyword;
    }

    public static String convert(String content, Map<EmailKeyword, String> matcher) {
        StringBuilder sb = new StringBuilder(content);
        for (Entry<EmailKeyword, String> entry : matcher.entrySet()) {
            replace(sb, entry.getKey().keyword, entry.getValue());
        }
        return sb.toString();
    }

    private static void replace(StringBuilder builder, String from, String to) {
        int index = builder.indexOf(from);
        while (index != -1) {
            builder.replace(index, index + from.length(), to);
            index += to.length();
            index = builder.indexOf(from, index);
        }
    }
}
