import styled from '@emotion/styled';

const Container = styled.div`
  width: 100vw;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

// Main Section
const MainSection = styled.div`
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
`;

const HightLight = styled.span`
  background: linear-gradient(90deg, #aa2298 0%, #841b76 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
`;

const Supporting = styled.p`
  font-family: 'Pretendard', sans-serif;
  font-weight: 500;
  font-size: 2.4rem;
  line-height: 1.4;

  color: ${({ theme }) => theme.baseColors.grayscale[700]};
`;

const CtaButtons = styled.div`
  display: flex;
  align-items: center;
  gap: 1.5rem;

  & > button {
    box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.25);
    -webkit-box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.25);
    -moz-box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.25);
  }
`;

const MainImg = styled.img`
  width: 60vw;
  min-width: 80rem;
  position: absolute;
  bottom: -18rem;
  z-index: 0;
`;

const ScrollDownArea = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;
  height: 30vh;

  position: absolute;
  bottom: -15vh;

  background: -moz-linear-gradient(
    0deg,
    rgba(255, 255, 255, 0) 0%,
    rgba(246, 228, 243, 0.8) 20%,
    rgba(243, 217, 238, 0.9) 50%,
    rgba(247, 229, 243, 0.8) 80%,
    rgba(255, 255, 255, 0) 100%
  );
  background: -webkit-linear-gradient(
    0deg,
    rgba(255, 255, 255, 0) 0%,
    rgba(246, 228, 243, 0.8) 20%,
    rgba(243, 217, 238, 0.9) 50%,
    rgba(247, 229, 243, 0.8) 80%,
    rgba(255, 255, 255, 0) 100%
  );
  background: linear-gradient(
    0deg,
    rgba(255, 255, 255, 0) 0%,
    rgba(246, 228, 243, 0.8) 20%,
    rgba(243, 217, 238, 0.9) 50%,
    rgba(247, 229, 243, 0.8) 80%,
    rgba(255, 255, 255, 0) 100%
  );

  & > svg {
    display: block;
    font-size: 4rem;
    color: ${({ theme }) => theme.baseColors.purplescale[500]};
  }
`;

// Pain Point Section
const PainPointSection = styled.div`
  position: relative;

  width: 100%;
  height: 100vh;

  display: flex;
  justify-content: center;
  align-items: center;

  background:
    -moz-linear-gradient(155deg, rgba(243, 217, 238, 1) 0%, rgba(255, 255, 255, 0) 100%),
    -moz-linear-gradient(0deg, rgba(255, 255, 255, 1) 0%, rgba(255, 255, 255, 0) 100%);
  background:
    -webkit-linear-gradient(155deg, rgba(243, 217, 238, 1) 0%, rgba(255, 255, 255, 0) 100%),
    -webkit-linear-gradient(0deg, rgba(255, 255, 255, 1) 0%, rgba(255, 255, 255, 0) 100%);
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
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;
`;

const SpeechBubble = styled.div`
  position: absolute;

  ${({ theme }) => theme.typography.common.large};

  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
  border-radius: 1rem;
  max-width: 29rem;
  padding: 0.8rem;

  box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.25);
  -webkit-box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.25);
  -moz-box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.25);

  &:nth-child(1) {
    top: 10%;
    left: 10%;
  }

  &:nth-child(2) {
    top: 10%;
    right: 10%;
  }

  &:nth-child(3) {
    top: 40%;
    left: 5%;
  }

  &:nth-child(4) {
    top: 40%;
    right: 5%;
  }

  &:nth-child(odd) {
    border-bottom-right-radius: 0;
  }

  &:nth-child(even) {
    border-bottom-left-radius: 0;
  }
`;

const PersonImg = styled.img`
  position: absolute;
  top: 25%;
  left: 25%;
`;

// Footer
const Footer = styled.footer`
  width: 100%;
  height: 10vh;

  display: flex;
  align-items: center;
  justify-content: center;

  color: ${({ theme }) => theme.baseColors.grayscale[900]};
  background-color: ${({ theme }) => theme.baseColors.grayscale[100]};

  & > p > a {
    ${({ theme }) => theme.typography.common.small};
    text-decoration: underline;
  }
`;

const S = {
  Container,
  MainSection,
  Catchphrase,
  HightLight,
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

  Footer,
};

export default S;
