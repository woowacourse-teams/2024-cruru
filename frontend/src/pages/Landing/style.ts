import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const dropdownShadow = css`
  box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.25);
  -webkit-box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.25);
  -moz-box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.25);
`;

const bounce = keyframes`
  0%, 20%, 50%, 80%, 100% {
    transform: translateY(0);
  }
  40% {
    transform: translateY(0.8rem);
  }
  60% {
    transform: translateY(0.8rem);
  }
`;

const fadeIn = keyframes`
  0% {
    opacity: 0.01;
  }
  100% {
    opacity: 1;
  }
`;

const fadeInUp = keyframes`
  0% {
    opacity: 0.01;
    transform: translateY(1rem);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
`;

const getBubblePosition = (index: number) => {
  const positions = [
    { top: '10%', right: '10%' },
    { top: '10%', left: '10%' },
    { top: '40%', right: '5%' },
    { top: '40%', left: '5%' },
  ];
  return positions[index] || positions[0];
};

const Container = styled.div`
  width: 100vw;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  white-space: nowrap;
`;

// Main Section
const MainSection = styled.section`
  position: relative;
  width: 100%;
  height: 100vh;
  padding-top: 20vh;

  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 3.6rem;

  text-align: center;

  background: linear-gradient(90deg, #f5f5ff 0%, ${({ theme }) => theme.baseColors.grayscale[50]} 49%);

  overflow: hidden;
`;

const Catchphrase = styled.h1`
  font-family: 'Noto Sans KR', sans-serif;
  font-weight: 900;
  font-size: 6.4rem;
  letter-spacing: -1px;

  span {
    opacity: 0;
    display: inline-block;
    animation: ${fadeIn} 0.5s ease-in-out forwards;
    white-space: pre-wrap;
  }

  span:nth-of-type(1) {
    animation-delay: 0s;
  }

  span:nth-of-type(2) {
    animation-delay: 0.1s;
  }

  span:nth-of-type(3) {
    animation-delay: 0.2s;
  }

  span:nth-of-type(4) {
    animation-delay: 0.3s;

    background: linear-gradient(90deg, #aa2298 0%, #841b76 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
  }
`;

const Supporting = styled.p`
  font-weight: 500;
  font-size: 2.4rem;
  line-height: 1.4;

  color: ${({ theme }) => theme.baseColors.grayscale[700]};

  opacity: 0;
  animation: ${fadeInUp} 0.5s ease-in-out forwards;
  animation-delay: 0.6s;
`;

const CtaButtons = styled.div`
  display: flex;
  align-items: center;
  gap: 1.5rem;

  opacity: 0;
  animation: ${fadeInUp} 0.5s ease-in-out forwards;
  animation-delay: 0.6s;

  & > button {
    ${dropdownShadow}
  }
`;

const MainImg = styled.img`
  width: 60vw;
  min-width: 80rem;
  position: absolute;
  bottom: -18rem;
  z-index: 0;

  opacity: 0;
  animation: ${fadeInUp} 0.5s ease-in-out forwards;
  animation-delay: 0.6s;
`;

const ScrollDownArea = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;
  height: 20vh;

  position: absolute;
  bottom: -10vh;

  & > svg {
    display: block;
    position: relative;
    bottom: -3rem;

    font-size: 4rem;
    color: ${({ theme }) => theme.baseColors.purplescale[500]};

    animation: ${bounce} 2s infinite;
  }
`;

// Pain Point Section
const PainPointSection = styled.section`
  position: relative;

  width: 100%;
  height: 100vh;

  display: flex;
  justify-content: center;
  align-items: center;

  background: linear-gradient(155deg, rgba(243, 217, 238, 1) 0%, rgba(255, 255, 255, 0) 100%),
    linear-gradient(0deg, rgba(255, 255, 255, 1) 0%, rgba(255, 255, 255, 0) 100%);
`;

const MessageImg = styled.img`
  width: 20%;
`;

const PersonImgWrapper = styled.div`
  position: relative;

  width: 100%;
  height: 80%;
  max-width: 85rem;
  max-height: 43rem;
`;
const SpeechBubbleContainer = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
`;

const SpeechBubble = styled.div<{ index: number }>`
  position: absolute;
  ${({ theme }) => theme.typography.common.large};
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
  border-radius: 1rem;
  max-width: 29rem;
  padding: 0.8rem;

  ${dropdownShadow}

  white-space: normal;

  ${({ index }) => getBubblePosition(index)};

  ${({ index }) => (index % 2 === 1 ? 'border-bottom-right-radius: 0;' : 'border-bottom-left-radius: 0;')};
`;

const PersonImg = styled.img`
  position: absolute;
  top: 25%;
  left: 25%;
`;

// Product Intro Section
const ProductIntroSection = styled.section`
  width: 100vw;
  height: 100vh;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 3.6rem;
`;

const IntroText = styled.p`
  font-size: 3.6rem;
  font-weight: 500;
  line-height: 1.5;
  text-align: center;

  background: linear-gradient(
    180deg,
    ${({ theme }) => theme.baseColors.grayscale[700]} 30%,
    ${({ theme }) => theme.baseColors.grayscale[600]} 70%,
    ${({ theme }) => theme.baseColors.grayscale[500]} 100%
  );
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
`;

const StrongIntroText = styled.p`
  font-size: 4.8rem;
  font-weight: 700;
  line-height: 1.5;
  text-align: center;
`;

const StrongIntroHighlightText = styled.span`
  color: ${({ theme }) => theme.colors.brand.primary};
`;

// Feature Section
const FeatureSection = styled.section<{ color: 'blue' | 'purple' | 'gray' }>`
  position: relative;
  width: 100vw;
  height: 100vh;
  padding: 4rem 12rem;

  display: flex;
  justify-content: space-evenly;
  align-items: center;
  flex-wrap: wrap;

  &:nth-of-type(odd) {
    flex-direction: row-reverse;
  }

  ${({ color }) => {
    const colors = {
      blue: '214, 236, 255',
      purple: '246, 232, 238',
      gray: '243, 243, 243',
    };

    const selectedColor = colors[color] || colors.gray;

    return css`
      background: linear-gradient(
        0deg,
        rgba(${selectedColor}, 0) 0%,
        rgba(${selectedColor}, 0.5) 10%,
        rgba(${selectedColor}, 1) 75%,
        rgba(${selectedColor}, 0.5) 90%,
        rgba(${selectedColor}, 0) 100%
      );
    `;
  }}
`;

const FeatureTitle = styled.h2`
  font-size: 4rem;
  font-weight: 700;
  margin-bottom: 3.2rem;
  line-height: 1.2;

  color: ${({ theme }) => theme.baseColors.grayscale[900]};
`;

const FeatureSubtitle = styled.h3<{ color: 'blue' | 'purple' | 'gray' }>`
  font-size: 2rem;
  font-weight: 600;
  margin-bottom: 1.6rem;

  color: ${({ theme, color }) => {
    const colorMap = {
      blue: '#007AFF',
      purple: theme.colors.brand.primary,
      gray: theme.baseColors.grayscale[700],
    };

    return colorMap[color] || theme.baseColors.grayscale[700];
  }};
`;

const FeatureDescription = styled.p`
  font-size: 1.8rem;
  font-weight: 500;
  line-height: 1.5;

  color: ${({ theme }) => theme.colors.text.default};
`;

const FeatureImg = styled.img`
  width: 100%;
  max-width: 55rem;
  min-width: 40rem;
`;

// Footer
const Footer = styled.footer`
  margin-top: 20rem;
  width: 100%;
  height: 20vh;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 0.8rem;

  color: ${({ theme }) => theme.baseColors.grayscale[900]};
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
  border-top: 1px solid ${({ theme }) => theme.baseColors.grayscale[300]};

  ${({ theme }) => theme.typography.common.default};

  & > p > a {
    ${({ theme }) => theme.typography.common.small};
    text-decoration: underline;
  }
`;

const StartButtonWrapper = styled.div`
  position: absolute;
  bottom: 0;

  width: 30%;
  height: 4.8rem;

  display: flex;
  justify-content: center;
  align-items: center;
`;

const S = {
  Container,
  MainSection,
  Catchphrase,
  Supporting,
  CtaButtons,
  MainImg,
  ScrollDownArea,

  PainPointSection,
  MessageImg,
  PersonImgWrapper,
  PersonImg,
  SpeechBubbleContainer,
  SpeechBubble,

  ProductIntroSection,
  IntroText,
  StrongIntroText,
  StrongIntroHighlightText,

  FeatureSection,
  FeatureTitle,
  FeatureSubtitle,
  FeatureDescription,
  FeatureImg,
  StartButtonWrapper,

  Footer,
};

export default S;
