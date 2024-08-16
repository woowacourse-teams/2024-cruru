import { Link } from 'react-router-dom';
import InputField from '@components/common/InputField';
import Button from '@components/common/Button';
import S from './style';

export default function SignIn() {
  const handleSignUp = () => {
    // TODO: 로그인 처리 로직을 추가하세요
    window.alert('로그인 버튼 클릭');
  };

  return (
    <S.SignUpContainer>
      <S.Title>로그인</S.Title>
      <InputField
        placeholder="이메일"
        type="email"
      />
      <InputField
        placeholder="비밀번호"
        type="password"
      />
      <S.ButtonContainer>
        <Button
          onClick={handleSignUp}
          size="fillContainer"
          color="primary"
        >
          로그인
        </Button>
      </S.ButtonContainer>
      <S.LinkContainer>
        {/* <Link to="#">비밀번호 찾기</Link> */}
        <Link to="/sign-up">회원가입</Link>
      </S.LinkContainer>
    </S.SignUpContainer>
  );
}
