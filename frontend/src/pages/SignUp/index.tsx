import Button from '@components/_common/atoms/Button';
import InputField from '@components/_common/molecules/InputField';
import Spinner from '@components/_common/atoms/Spinner';
import { validatePhoneNumber } from '@domain/validations/apply';
import useSignUp from '@hooks/useSignUp';
import useForm from '@hooks/utils/useForm';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useToast } from '@contexts/ToastContext';
import PasswordInput from './PasswordInput';
import S from './style';
import EmailVerifyField from './EmailVerifyField';

export default function SignUp() {
  const { formData, register, errors } = useForm({
    initialValues: { email: '', phone: '', password: '', clubName: '' },
  });
  const { signUpMutate } = useSignUp();
  const [passwordError, setPasswordError] = useState(false);
  const [isVerify, setIsVerify] = useState(false);

  const toast = useToast();

  const handleSignUp: React.FormEventHandler = (event) => {
    event.preventDefault();
    if (!isVerify) {
      toast.error('이메일 인증이 이루어지지 않았습니다.');
      return;
    }

    if (Object.values(errors).some((error) => error)) {
      toast.error('회원가입 정보를 확인해주세요.');
      return;
    }

    if (passwordError) {
      toast.error('비밀번호를 확인해주세요.');
      return;
    }

    signUpMutate.mutate({ ...formData });
  };

  const deleteHangulFormatter = (value: string) => {
    const koreanRegex = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
    return value.replace(koreanRegex, '');
  };

  const setPasswordValid = (bol: boolean) => {
    setPasswordError(bol);
  };

  return (
    <S.Layout>
      <S.SignInContainer onSubmit={handleSignUp}>
        <S.Title>
          회원가입
          <S.Description>기본 정보를 입력하세요.</S.Description>
        </S.Title>
        <EmailVerifyField
          register={register}
          isVerify={isVerify}
          setIsVerify={setIsVerify}
        />
        {isVerify && (
          <S.InnerAnimateForm>
            <InputField
              {...register('phone', {
                validate: validatePhoneNumber,
                placeholder: '전화번호',
                inputMode: 'numeric',
                maxLength: 11,
                type: 'text',
              })}
              disabled={!isVerify}
              label="전화번호"
              required
            />
            <PasswordInput
              {...register('password', { formatter: deleteHangulFormatter, minLength: 8, maxLength: 32 })}
              disabled={!isVerify}
              error={passwordError}
              setPasswordValid={setPasswordValid}
            />
            <InputField
              {...register('clubName', { placeholder: '동아리 이름', type: 'text', maxLength: 20 })}
              disabled={!isVerify}
              label="동아리 이름"
              required
            />
          </S.InnerAnimateForm>
        )}

        <S.ButtonContainer>
          <Button
            size="fillContainer"
            color="primary"
            type="submit"
            disabled={!isVerify}
          >
            {signUpMutate.isPending ? <Spinner width={40} /> : '회원가입'}
          </Button>
        </S.ButtonContainer>

        <S.LinkContainer>
          <Link to="/sign-in">로그인</Link>
        </S.LinkContainer>
      </S.SignInContainer>
    </S.Layout>
  );
}
