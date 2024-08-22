import { useNavigate } from 'react-router-dom';
import Logo from '@assets/images/logo.svg';
import { useEffect } from 'react';
import S from './style';

export default function Landing() {
  const navigate = useNavigate();

  useEffect(() => {
    setTimeout(() => navigate('/sign-in'), 2000);
  }, [navigate]);

  return (
    <S.Container>
      <S.Logo
        src={Logo}
        alt="크루루 로고"
      />
    </S.Container>
  );
}
