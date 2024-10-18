import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';
import media from '@styles/media';

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

const getBubblePosition = (index: number, isMobile?: boolean) => {
  const desktopPositions = [
    { top: '10%', right: '10%' },
    { top: '10%', left: '10%' },
    { top: '40%', right: '5%' },
    { top: '40%', left: '5%' },
  ];

  const mobilePositions = [
    { right: '5%', top: '10%' },
    { left: '5%', top: '10%' },
    { right: '0', top: '40%' },
    { left: '0', top: '40%' },
  ];

  const position = isMobile
    ? mobilePositions[index] || mobilePositions[0]
    : desktopPositions[index] || desktopPositions[0];

  return css`
    ${position.top ? `top: ${position.top};` : ''}
    ${position.right ? `right: ${position.right};` : ''}
    ${position.left ? `left: ${position.left};` : ''}
  `;
};

const Container = styled.div`
  position: relative;

  width: 100vw;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  white-space: nowrap;
`;

const MobileHeader = styled.header`
  display: none;
  position: fixed;
  top: 1.6rem;
  padding: 2.4rem 2.2rem;
  width: 90%;
  border-radius: 1.6rem;

  z-index: 10;
  box-shadow: 0px 4px 4px rgba(144, 144, 144, 0.1);
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 0%,
    rgba(255, 255, 255, 0.5) 6%,
    rgba(255, 255, 255, 1) 100%
  );
  border: 1px solid linear-gradient(90deg, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 1) 100%);

  ${media('mobile')`
    display: flex;
    justify-content: space-between;
  `}
`;

const ServiceLogo = styled.img`
  width: 5rem;
`;

const HeaderLogin = styled.div`
  ${({ theme }) => theme.typography.common.small};
`;

// Main Section
const MainSection = styled.section`
  position: relative;
  width: 100%;
  padding-top: 20vh;

  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 3.6rem;

  text-align: center;

  background: linear-gradient(90deg, #f5f5ff 0%, ${({ theme }) => theme.baseColors.grayscale[50]} 49%);

  ${media('mobile')`
    height: 100vh;
    padding: 20vh 1.6rem;
    gap: 1.6rem;
  `}
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

  ${media('mobile')(`
    font-size: 3.5rem;
    line-height: 1.4;
  `)}
`;

const Supporting = styled.p`
  font-weight: 500;
  font-size: 2.4rem;
  line-height: 1.4;

  color: ${({ theme }) => theme.baseColors.grayscale[700]};

  opacity: 0;
  animation: ${fadeInUp} 0.5s ease-in-out forwards;
  animation-delay: 0.6s;

  ${media('mobile')(`
    font-size: 1.6rem;
  `)}
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
  width: 60%;
  min-width: 80rem;

  opacity: 0;
  animation: ${fadeInUp} 0.5s ease-in-out forwards;
  animation-delay: 0.6s;

  ${media('mobile')`
    width: 80%;
    min-width: 40rem;
    margin-top: 1.6rem;
  `}
`;

const ScrollDownArea = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;
  height: 20vh;

  position: absolute;
  bottom: -10vh;

  & > button > svg {
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

  ${media('mobile')`
    padding: 5.4rem 1.6rem 2.4rem;
    height: fit-content;
    background: none;
  `}
`;

const MessageImg = styled.img`
  width: 20%;

  ${media('mobile')`
    display: none;
  `}
`;

const PersonImgWrapper = styled.div`
  position: relative;

  width: 100%;
  height: 80%;
  max-width: 85rem;
  max-height: 43rem;

  ${media('mobile')`
    height: 50vh;
  `}
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
  max-width: 25rem;
  padding: 0.8rem;

  ${dropdownShadow}

  white-space: normal;

  ${({ index }) => getBubblePosition(index)};

  ${({ index }) => (index % 2 === 1 ? 'border-bottom-right-radius: 0;' : 'border-bottom-left-radius: 0;')};

  ${({ index }) =>
    media('mobile')(`
    max-width: 40%;
    font-size: 1.4rem;
    ${index === 0 ? 'right: 5%;' : ''}
    ${index === 1 ? 'left: 5%;' : ''}
    ${index === 2 ? 'right: 0;' : ''}
    ${index === 3 ? 'left: 0;' : ''}
  `)}
`;

const PersonImg = styled.img`
  position: absolute;
  top: 25%;
  left: 25%;

  ${media('mobile')`
    width: 80%;
    top: 20%;
    left: 10%;
  `}
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

  ${media('mobile')`
    padding: 0 1.6rem;
    height: fit-content;
    margin-bottom: 5.4rem;
  `}
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

  ${media('mobile')`
    font-size: 2.4rem;
  `}
`;

const StrongIntroText = styled.p`
  font-size: 4.8rem;
  font-weight: 700;
  line-height: 1.5;
  text-align: center;

  ${media('mobile')`
    font-size: 3rem;
  `}
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

  ${media('mobile')`
    padding: 0 1.6rem;
    height: fit-content;
    text-align: center;
    gap: 2.4rem;
    margin-bottom: 4.8rem;
  `}
`;

const FeatureTitle = styled.h2`
  font-size: 4rem;
  font-weight: 700;
  margin-bottom: 3.2rem;
  line-height: 1.2;

  color: ${({ theme }) => theme.baseColors.grayscale[900]};

  ${media('mobile')`
    font-size: 3rem;
  `}
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

  ${media('mobile')`
    font-size: 1.4rem;
  `}
`;

const FeatureImg = styled.img`
  width: 100%;
  max-width: 55rem;
  min-width: 40rem;

  ${media('mobile')`
    width: 90%;
    min-width: auto;
  `}
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
  MobileHeader,
  ServiceLogo,
  HeaderLogin,

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
