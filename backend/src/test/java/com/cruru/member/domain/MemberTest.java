package com.cruru.member.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cruru.member.exception.badrequest.MemberIllegalPasswordException;
import com.cruru.member.exception.badrequest.MemberIllegalPhoneNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("회원 도메인 테스트")
class MemberTest {

    @DisplayName("허용된 값들로 Member를 생성한다.")
    @ParameterizedTest
    @CsvSource({
            "test1@example.com, Password123!, 01012345678",
            "test2@example.com, Pass@1234, 0212345678",
            "test3@example.com, MyPassword1!, 0311234567"
    })
    void createMember(String email, String password, String phone) {
        // given&when&then
        assertDoesNotThrow(() -> new Member(email, password, phone));
    }

    @DisplayName("허용되지 않는 비밀번호 형식으로 Member 생성 시 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "NoNumber!",    // 숫자가 없음
            "NoSpecial123" // 특수문자가 없음
    })
    void createMemberWithInvalidPassword(String password) {
        // given
        String email = "test@example.com";
        String phone = "01012345678";

        // when & then
        assertThrows(MemberIllegalPasswordException.class, () -> new Member(email, password, phone));
    }

    @DisplayName("허용되지 않는 전화번호로 Member 생성 시 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "0123456789",      // 잘못된 시작번호
            "010-1234-567",    // 형식에 맞지 않음
            "031-1234-56789",  // 형식에 맞지 않음
            "02123456789",      // 형식에 맞지 않음
            "1",            // 짧은 전화번호
            "phonenumber", // 번호가 없는 문자열
            "123451234512345", // 길이 초과 전화번호
            "a1012341234" // 문자가 포함된 전화번호
    })
    void createMemberWithInvalidPhoneNumber(String phone) {
        // given
        String email = "test@example.com";
        String password = "ValidPassword123!";

        // when&then
        assertThrows(MemberIllegalPhoneNumberException.class, () -> new Member(email, password, phone));
    }
}
