import styled from '@emotion/styled';

const Container = styled.div`
  width: 100vw;
  height: 100vh;

  display: flex;
  justify-content: center;
  align-items: center;
`;

// Main Section
const MainSection = styled.div`
  position: relative;
  width: 100%;
  height: 100vh;
  padding-top: 10rem;

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
  width: 93.8rem;
  position: absolute;
  bottom: -18rem;
  z-index: 0;
`;

const ScrollDownArea = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 10vh;

  position: absolute;
  bottom: 0;

  background: rgb(243, 217, 238);
  background: -moz-linear-gradient(
    0deg,
    rgba(243, 217, 238, 1) 0%,
    rgba(255, 255, 255, 0) 50%,
    rgba(255, 255, 255, 0) 100%
  );
  background: -webkit-linear-gradient(
    0deg,
    rgba(243, 217, 238, 1) 0%,
    rgba(255, 255, 255, 0) 50%,
    rgba(255, 255, 255, 0) 100%
  );
  background: linear-gradient(0deg, rgba(243, 217, 238, 1) 0%, rgba(255, 255, 255, 0) 50%, rgba(255, 255, 255, 0) 100%);
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#f3d9ee",endColorstr="#ffffff",GradientType=1);

  & > svg {
    display: block;
    font-size: 4rem;
    color: ${({ theme }) => theme.baseColors.purplescale[500]};
  }
`;

// Pain Point Section
const PainPointSection = styled.div`
  width: 100%;
  height: 100vh;
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
};

export default S;
