package com.cruru.email.domain;

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

    public String replace(String input, String replacement) {
        return input.replace(keyword, replacement);
    }
}
