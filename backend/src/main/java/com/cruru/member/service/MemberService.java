package com.cruru.member.service;

import com.cruru.auth.security.PasswordValidator;
import com.cruru.member.controller.dto.MemberCreateRequest;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.member.exception.MemberEmailDuplicatedException;
import com.cruru.member.exception.MemberNotFoundException;
import com.cruru.member.exception.badrequest.MemberIllegalPasswordException;
import com.cruru.member.exception.badrequest.MemberPasswordLengthException;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 32;
    private static final Pattern VALID_PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).*$");

    private final MemberRepository memberRepository;
    private final PasswordValidator passwordValidator;

    @Transactional
    public Member create(MemberCreateRequest request) {
        boolean exists = memberRepository.existsByEmail(request.email());
        if (exists) {
            throw new MemberEmailDuplicatedException();
        }

        String encodedPassword = generateEncodedPassword(request);
        Member newMember = new Member(request.email(), encodedPassword, request.phone());
        return memberRepository.save(newMember);
    }

    private String generateEncodedPassword(MemberCreateRequest request) {
        String rawPassword = request.password();
        validatePassword(rawPassword);
        return passwordValidator.encode(rawPassword);
    }

    private void validatePassword(String rawPassword) {
        if (rawPassword.length() < PASSWORD_MIN_LENGTH || rawPassword.length() > PASSWORD_MAX_LENGTH) {
            throw new MemberPasswordLengthException(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH, rawPassword.length());
        }
        if (!VALID_PASSWORD_PATTERN.matcher(rawPassword).matches()) {
            throw new MemberIllegalPasswordException();
        }
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }
}
