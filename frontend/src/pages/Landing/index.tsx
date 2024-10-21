import { routes } from '@router/path';
import { Link, useNavigate } from 'react-router-dom';

import Logo from '@assets/images/logo.svg';
import DashboardWebp from '@assets/images/dashboard.webp';
import Feature1Webp from '@assets/images/feature1.webp';
import Feature2Webp from '@assets/images/feature2.webp';
import Feature3Webp from '@assets/images/feature3.webp';
import ManStrugglingWebp from '@assets/images/manStruggling.webp';
import MessageWebp from '@assets/images/message.webp';
import { HiChevronDown, HiChevronUp } from 'react-icons/hi2';

import Button from '@components/_common/atoms/Button';
import IconButton from '@components/_common/atoms/IconButton';
import { useRef } from 'react';
import S from './style';

const SPEECH_BUBBLE_TEXTS = [
  '면접관 여러 명이 지원자 평가를 어떻게 남기고 공유할 수 있지?',
  '지원자들에게 일일이 연락하는게 너무 번거로워..',
  '지원자 정보랑 평가가 한눈에 안들어와...',
  '여러 공고를 한 번에 관리하기가 힘드네..',
];

export default function Landing() {
  const painPointRef = useRef<HTMLDivElement>(null);
  const navigate = useNavigate();

  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const scrollToPainPoint = () => {
    if (painPointRef.current) {
      painPointRef.current.scrollIntoView({
        behavior: 'smooth',
      });
    }
  };

  return (
    <S.Container>
      <IconButton
        size="md"
        shape="round"
        outline={false}
        onClick={scrollToTop}
        style={{ position: 'fixed', bottom: '4.8rem', right: '4.8rem', zIndex: 10, padding: '0.8rem' }}
      >
        <HiChevronUp />
      </IconButton>

      <S.MobileHeader>
        <S.ServiceLogo
          src={Logo}
          alt="크루루 서비스 로고"
        />
        <S.HeaderLogin>
          <Link to={routes.signIn()}>로그인</Link>
        </S.HeaderLogin>
      </S.MobileHeader>

      <S.MainSection>
        <S.Catchphrase>
          <span>복잡했던 </span>
          <span>리크루팅,</span>
          <br />
          <span>하루만에</span>
          <span> 크루루.</span>
        </S.Catchphrase>
        <S.Supporting>
          번거로운 엑셀 작업은 이제 그만!
          <br />
          리크루팅의 모든 단계를 크루루와 함께 해결하세요.
        </S.Supporting>

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

        <S.MainImg
          src={DashboardWebp}
          alt="지원자 현황을 한 눈에 확인할 수 있는 대시보드 페이지"
        />

        <S.ScrollDownArea>
          <button
            type="button"
            onClick={scrollToPainPoint}
          >
            <HiChevronDown />
          </button>
        </S.ScrollDownArea>
      </S.MainSection>

      <S.PainPointSection ref={painPointRef}>
        <S.MessageImg
          src={MessageWebp}
          alt="복잡한 모집과정에 괴로워하는 유저의 메시지 대화 내용"
        />
        <S.PersonImgWrapper>
          <S.SpeechBubbleContainer>
            {SPEECH_BUBBLE_TEXTS.map((text, index) => (
              <S.SpeechBubble
                // eslint-disable-next-line react/no-array-index-key
                key={index}
                index={index}
              >
                {text}
              </S.SpeechBubble>
            ))}
          </S.SpeechBubbleContainer>

          <S.PersonImg
            src={ManStrugglingWebp}
            alt="복잡한 모집과정에 괴로워하는 유저의 모습"
          />
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
          {/* eslint-disable-next-line */}
          <S.StrongIntroHighlightText>크루루</S.StrongIntroHighlightText>로 한 번에 해결하세요.
        </S.StrongIntroText>
      </S.ProductIntroSection>

      <S.FeatureSection color="blue">
        <div>
          <S.FeatureSubtitle color="blue">원스톱 지원자 관리</S.FeatureSubtitle>
          <S.FeatureTitle>
            한눈에 파악하고
            <br />
            빠르게 결정하는 리크루팅
          </S.FeatureTitle>
          <S.FeatureDescription>
            복잡한 엑셀 작업은 그만!
            <br />
            클릭 한 번으로 지원자의 정보를 손쉽게 살펴보세요.
            <br />
            크루루와 함께라면, 오직 우수한 지원자를
            <br />
            찾는 일에만 집중하실 수 있습니다.
          </S.FeatureDescription>
        </div>

        <S.FeatureImg
          src={Feature1Webp}
          alt="한눈에 파악하고 빠르게 결정하는 리크루팅"
        />
      </S.FeatureSection>

      <S.FeatureSection color="purple">
        <div>
          <S.FeatureSubtitle color="purple">맞춤형 공고 제작</S.FeatureSubtitle>
          <S.FeatureTitle>
            인재들이 선호하는
            <br />
            매력적인 공고 관리
          </S.FeatureTitle>
          <S.FeatureDescription>
            답답한 폼 작업 이젠 안녕!
            <br />
            다양한 질문 유형이 포함된 지원서를
            <br />
            원하는 대로 만들 수 있습니다.
            <br />
            우리 팀에 꼭 필요한 지원자의 관심을 사로잡으세요.
          </S.FeatureDescription>
        </div>

        <S.FeatureImg
          src={Feature2Webp}
          alt="인재들이 선호하는 매력적인 공고 관리"
        />
      </S.FeatureSection>

      <S.FeatureSection color="gray">
        <div>
          <S.FeatureSubtitle color="gray">심플한 지원 경험</S.FeatureSubtitle>
          <S.FeatureTitle>
            지원자를 사로잡는
            <br />
            스마트한 첫 만남
          </S.FeatureTitle>
          <S.FeatureDescription>
            아직도 이메일로 지원서를 받고 계신가요?
            <br />
            간결하고 직관적인 지원 페이지로 변화를 만들어보세요.
            <br />
            심플한 지원 경험이 여러분의 팀 이미지를
            <br />
            한층 더 높여줄 것입니다.
          </S.FeatureDescription>
        </div>

        <S.FeatureImg
          src={Feature3Webp}
          alt="지원자를 사로잡는 스마트한 첫 만남"
        />
      </S.FeatureSection>

      <S.StartButtonContainer>
        <Button
          size="fillContainer"
          onClick={() => navigate(routes.signUp())}
          color="primary"
        >
          크루루 새로 시작하기
        </Button>
      </S.StartButtonContainer>

      <S.Footer>
        <p>Copyright © 2024 Cruru, All rights reserved.</p>
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
