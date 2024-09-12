import Button from '@components/common/Button';

import { routes } from '@router/path';
import { useNavigate } from 'react-router-dom';

import DashboardPng from '@assets/images/dashboard.png';
import ManStrugglingPng from '@assets/images/manStruggling.png';
import MessagePng from '@assets/images/message.png';
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
          리크루팅의 모든 단계를 크루루와 함께 해결하세요.
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

      <S.PainPointSection>
        <S.MessageImg src={MessagePng} />
        <S.PersonImgWrapper>
          <S.PersonImg
            src={ManStrugglingPng}
            alt="복잡한 모집과정에 괴로워하는 유저의 모습"
          />

          <S.SpeechBubbleContainer>
            <S.SpeechBubble>면접관 여러 명이 지원자 평가를 어떻게 남기고 공유할 수 있지?</S.SpeechBubble>
            <S.SpeechBubble>지원자들에게 일일이 연락하는게 너무 번거로워..</S.SpeechBubble>
            <S.SpeechBubble>지원자 정보랑 평가가 한눈에 안들어와...</S.SpeechBubble>
            <S.SpeechBubble>여러 공고를 한 번에 관리하기가 힘드네..</S.SpeechBubble>
          </S.SpeechBubbleContainer>
        </S.PersonImgWrapper>
      </S.PainPointSection>

      <S.ProductIntroSection>
        <S.IntroText>
          번거로운 지원 관리
          <br />
          까다로운 공고 제작
          <br />
          불필요한 관리 업무
        </S.IntroText>
        <S.StrongIntroText>
          <S.StrongIntroHighlightText>크루루</S.StrongIntroHighlightText>로 한 번에 해결하세요.
        </S.StrongIntroText>
      </S.ProductIntroSection>

      <S.Footer>
        <p>
          Illustration by&nbsp;
          <a href="https://icons8.com/illustrations/author/zD2oqC8lLBBA">Icons 8</a>
          &nbsp;from&nbsp;
          <a href="https://icons8.com/illustrations">Ouch!</a>
        </p>
      </S.Footer>
    </S.Container>
  );
}
