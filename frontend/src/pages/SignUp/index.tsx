import { Link } from 'react-router-dom';
import InputField from '@components/common/InputField';
import Button from '@components/common/Button';
import useSignUp from '@hooks/useSignUp';
import useForm from '@hooks/utils/useForm';
import { validateEmail, validatePhoneNumber } from '@domain/validations/apply';
import { formatPhoneNumber } from '@utils/formatPhoneNumber';
import PasswordInput from './PasswordInput';
import S from './style';

export default function SignUp() {
  const { formData, register } = useForm({ initialValues: { email: '', phone: '', password: '', clubName: '' } });
  const { signUpMutate } = useSignUp();

  const handleSignUp: React.FormEventHandler = (event) => {
    event.preventDefault();
    signUpMutate.mutate(formData);
  };

  const deleteHangulFormatter = (value: string) => {
    const koreanRegex = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
    return value.replace(koreanRegex, '');
  };

  return (
    <S.SignInContainer onSubmit={handleSignUp}>
      <S.Title>
        회원가입
        <S.Description>기본 정보를 입력하세요.</S.Description>
      </S.Title>
      <InputField
        {...register('email', { validate: validateEmail, placeholder: '이메일', type: 'email' })}
        label="이메일"
        required
      />
      <InputField
        {...register('phone', {
          validate: validatePhoneNumber,
          formatter: formatPhoneNumber,
          placeholder: '전화번호',
          inputMode: 'numeric',
          maxLength: 13,
          type: 'text',
        })}
        label="전화번호"
        required
      />
      <PasswordInput {...register('password', { formatter: deleteHangulFormatter, minLength: 8, maxLength: 32 })} />
      <InputField
        {...register('clubName', { placeholder: '동아리 이름', type: 'text', maxLength: 20 })}
        label="동아리 이름"
        required
      />
      <S.ButtonContainer>
        <Button
          size="fillContainer"
          color="primary"
          type="submit"
        >
          회원가입
        </Button>
      </S.ButtonContainer>

      <S.LinkContainer>
        <Link to="/sign-in">로그인</Link>
      </S.LinkContainer>
    </S.SignInContainer>
  );
}
