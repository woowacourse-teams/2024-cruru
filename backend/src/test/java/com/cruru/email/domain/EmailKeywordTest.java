package com.cruru.email.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EmailKeywordTest {

    @DisplayName("입력된 문자열을 입력에 따라 변환한다.")
    @Test
    void convert() {
        // given
        Map<EmailKeyword, String> input = Map.of(
                EmailKeyword.APPLICANT_NAME, "김형호",
                EmailKeyword.CLUB_NAME, "크루루",
                EmailKeyword.APPLY_FORM_TITLE, "백엔드 모집 공고",
                EmailKeyword.PROCESS_NAME, "서류 접수"
        );
        String content = "{AP_NAME}, {CL_NAME}, {RC_TITLE}, {RC_STEP}";

        // when
        String actual = EmailKeyword.convert(content, input);

        // then
        assertThat(actual).isEqualTo("김형호, 크루루, 백엔드 모집 공고, 서류 접수");
    }
}
