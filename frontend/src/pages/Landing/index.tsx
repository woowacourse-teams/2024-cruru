import Button from '@components/common/Button';

import { routes } from '@router/path';
import { useNavigate } from 'react-router-dom';

import DashboardPng from '@assets/images/dashboard.png';
import { HiChevronDown } from 'react-icons/hi2';

import S from './style';

export default function Landing() {
  const navigate = useNavigate();
  return (
    <S.Container>
      <S.MainSection>
        <S.Catchphrase>
          복잡했던 리크루팅,
          <br />
          하루만에
          <S.HightLight> 크루루.</S.HightLight>
        </S.Catchphrase>
        <S.Supporting>
          번거로운 엑셀 작업은 이제 그만!
          <br />
          리크루팅의 모든 단계를 크루루와 함께 해결하세요
        </S.Supporting>

        <S.MainImg
          src={DashboardPng}
          alt="지원자 현황을 한 눈에 확인할 수 있는 대시보드 페이지"
        />

        <S.CtaButtons>
          <Button
            size="md"
            onClick={() => navigate(routes.signIn())}
            color="white"
          >
            로그인하기
          </Button>
          <Button
            size="md"
            onClick={() => navigate(routes.signUp())}
            color="primary"
          >
            크루루 새로 시작하기
          </Button>
        </S.CtaButtons>

        <S.ScrollDownArea>
          <HiChevronDown />
        </S.ScrollDownArea>
      </S.MainSection>
    </S.Container>
  );
}
