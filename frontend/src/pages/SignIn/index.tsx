import { Link } from 'react-router-dom';

import Button from '@components/_common/atoms/Button';
import Spinner from '@components/_common/atoms/Spinner';
import InputField from '@components/_common/molecules/InputField';

import useForm from '@hooks/utils/useForm';
import useSignIn from '@hooks/useSignIn';
import S from './style';

export default function SignIn() {
  const { formData, register } = useForm({ initialValues: { email: '', password: '' } });
  const { signInMutate } = useSignIn();

  const handleSignIn: React.FormEventHandler = (event) => {
    event.preventDefault();
    signInMutate.mutate(formData);
  };

  return (
    <S.Layout>
      <S.SignUpContainer onSubmit={handleSignIn}>
        <S.Title>로그인</S.Title>
        <InputField
          {...register('email', { placeholder: '이메일', type: 'email' })}
          required
        />
        <InputField
          {...register('password', { placeholder: '비밀번호', type: 'password' })}
          required
        />
        <S.ButtonContainer>
          <Button
            size="fillContainer"
            color="primary"
            type="submit"
          >
            {signInMutate.isPending ? <Spinner width={40} /> : '로그인'}
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
