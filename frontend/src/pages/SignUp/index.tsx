import { Link } from 'react-router-dom';
import InputField from '@components/common/InputField';
import Button from '@components/common/Button';
import S from './style';
import PasswordInput from './PasswordInput';

export default function SignUp() {
  const handleSignUp = () => {
    // TODO: 회원가입 처리를 위한 API를 연결해야 합니다.
    window.alert('회원가입 버튼 클릭');
  };

  // TODO: 비밀번호는 영어만 가능하기 때문에, validate에 한글검증이 들어가야 합니다.

  return (
    <S.SignInContainer>
      <S.Title>
        회원가입
        <S.Description>기본 정보를 입력하세요.</S.Description>
      </S.Title>
      <InputField
        label="이메일"
        placeholder="이메일"
        type="email"
      />
      <InputField
        label="이메일"
        placeholder="이메일"
        type="email"
      />
      <InputField
        label="이메일"
        placeholder="이메일"
        type="email"
      />
      <InputField
        label="동아리 이름"
        placeholder="동아리 이름"
        type="text"
      />
      <InputField
        label="전화번호"
        placeholder="전화번호"
        type="text"
      />
      <PasswordInput />
      <S.ButtonContainer>
        <Button
          onClick={handleSignUp}
          size="fillContainer"
          color="primary"
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
