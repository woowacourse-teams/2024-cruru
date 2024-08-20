import { Link } from 'react-router-dom';
import InputField from '@components/common/InputField';
import Button from '@components/common/Button';
import useForm from '@hooks/utils/useForm';
import useSignIn from '@hooks/useSignIn';
import S from './style';

export default function SignIn() {
  const { formData, register } = useForm({ initialValues: { email: '', password: '' } });
  const { signInMutate } = useSignIn();

  const handleSignUp: React.FormEventHandler = (event) => {
    event.preventDefault();
    signInMutate.mutate(formData);
  };

  return (
    <S.Layout>
      <S.SignUpContainer onSubmit={handleSignUp}>
        <S.Title>로그인</S.Title>
        <InputField {...register('email', { placeholder: '이메일', type: 'email' })} />
        <InputField {...register('password', { placeholder: '비밀번호', type: 'password' })} />
        <S.ButtonContainer>
          <Button
            size="fillContainer"
            color="primary"
            type="submit"
          >
            로그인
          </Button>
        </S.ButtonContainer>
        <S.LinkContainer>
          {/* <Link to="#">비밀번호 찾기</Link> */}
          <Link to="/sign-up">회원가입</Link>
        </S.LinkContainer>
      </S.SignUpContainer>
    </S.Layout>
  );
}
