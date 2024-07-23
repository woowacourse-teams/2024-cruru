package com.cruru.club.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.club.exception.ClubNameCharacterException;
import com.cruru.club.exception.ClubNameLengthException;
import com.cruru.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("동아리 도메인 테스트")
class ClubTest {

    @DisplayName("동아리 이름은 특수문자와 숫자를 허용한다.")
    @ValueSource(strings = {"club!!", "(club!@#$%^&*)", "CLUB123"})
    @ParameterizedTest
    void validClubName(String name) {
        // given
        Member member = new Member("password", "phoneNumber", "phone");

        // when&then
        assertThatCode(() -> new Club(name, member)).doesNotThrowAnyException();
    }

    @DisplayName("동아리 이름이 비어있거나 32자 초과시 예외가 발생한다.")
    @ValueSource(strings = {"", "ThisStringLengthIs33!!!!!!!!!!!!!"})
    @ParameterizedTest
    void invalidClubNameLength(String name) {
        // given
        Member member = new Member("password", "phoneNumber", "phone");

        // when&then
        assertThatThrownBy(() -> new Club(name, member)).isInstanceOf(ClubNameLengthException.class);
    }

    @DisplayName("동아리 이름에 허용되지 않은 글자가 들어가면 예외가 발생한다.")
    @ValueSource(strings = {"invalidCharacter|", "invalidCharacter\\"})
    @ParameterizedTest
    void invalidClubNameCharacter(String name) {
        // given
        Member member = new Member("password", "phoneNumber", "phone");

        // when&then
        assertThatThrownBy(() -> new Club(name, member)).isInstanceOf(ClubNameCharacterException.class);
    }
}
