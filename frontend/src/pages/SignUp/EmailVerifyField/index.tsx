import { useState } from 'react';

import Button from '@components/_common/atoms/Button';
import Spinner from '@components/_common/atoms/Spinner';
import { HiCheck } from 'react-icons/hi2';

import InputField from '@components/_common/molecules/InputField';
import { validateEmail } from '@domain/validations/apply';
import useEmailVerify from '@hooks/useEmailVerify';
import S from './style';

interface EmailVerifyFieldProps {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  register: any;
  isVerify: boolean;
  setIsVerify: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function EmailVerifyField({ register, isVerify, setIsVerify }: EmailVerifyFieldProps) {
  const [isSendVerifyEmail, setIsSendVerifyEmail] = useState(false);
  const [verificationCode, setVerificationCode] = useState('');

  const handleVerificationCodeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setVerificationCode(e.target.value);
  };

  const emailRegister = register('email', { validate: validateEmail, placeholder: '이메일', type: 'email' });

  const {
    postVerifyEmailMutate,
    isPendingPostVerifyEmail,
    confirmEmailVerificationMutate,
    isPendingConfirmEmailVerification,
  } = useEmailVerify({
    setIsSendVerifyEmail,
    setIsVerify,
  });

  const handleClickSendVerifyEmail = async () => {
    if (!emailRegister.value) return;
    if (emailRegister.error || isPendingPostVerifyEmail) return;
    postVerifyEmailMutate({ email: emailRegister.value });
  };

  const handleClickVerify = async () => {
    if (isPendingConfirmEmailVerification) return;
    confirmEmailVerificationMutate({ email: emailRegister.value, verificationCode });
  };

  return (
    <S.Wrapper>
      <S.FieldWrapper>
        <InputField
          {...emailRegister}
          label="이메일"
          disabled={isVerify}
          required
        />
        <S.ButtonWrapper>
          <Button
            type="button"
            size="md"
            color="secondary"
            disabled={isVerify}
            onClick={handleClickSendVerifyEmail}
          >
            <S.ButtonInner>
              {isPendingPostVerifyEmail ? (
                <Spinner
                  width={24}
                  color="primary"
                />
              ) : isSendVerifyEmail ? (
                '재전송'
              ) : (
                '메일 전송'
              )}
            </S.ButtonInner>
          </Button>
        </S.ButtonWrapper>
      </S.FieldWrapper>
      <S.FieldWrapper>
        <InputField
          value={verificationCode}
          onChange={handleVerificationCodeChange}
          disabled={!isSendVerifyEmail || isVerify}
          placeholder="인증 번호"
          required
        />
        <S.ConfirmButtonWrapper>
          <Button
            type="button"
            size="md"
            color="white"
            disabled={!isSendVerifyEmail || isVerify}
            disabledColor={isVerify ? 'success' : 'default'}
            onClick={handleClickVerify}
          >
            <S.ButtonInner>
              {isPendingConfirmEmailVerification ? (
                <Spinner
                  width={24}
                  color="black"
                />
              ) : isVerify ? (
                <HiCheck
                  size={24}
                  strokeWidth={1}
                />
              ) : (
                '인증 확인'
              )}
            </S.ButtonInner>
          </Button>
        </S.ConfirmButtonWrapper>
      </S.FieldWrapper>
    </S.Wrapper>
  );
}
