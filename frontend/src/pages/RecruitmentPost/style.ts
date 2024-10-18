import styled from '@emotion/styled';

const PageLayout = styled.div`
  width: 100vw;
  min-width: 30rem;
  height: 100vh;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;
  max-width: 70rem;
  height: 100%;

  padding: 4.8rem 1.6rem 0;
`;

const Header = styled.header`
  width: 100%;
  margin-bottom: 2rem;

  display: flex;
  flex-direction: column;
  gap: 1.2rem;
`;

const Title = styled.h1`
  ${({ theme }) => theme.typography.heading[900]};
  color: ${({ theme }) => theme.colors.text.default};
`;

const PeriodContainer = styled.p`
  display: flex;
  align-items: center;
  gap: 0.4rem;

  color: ${({ theme }) => theme.baseColors.grayscale[600]};

  svg {
    width: 1.6rem;
    height: 1.6rem;
  }
`;

const Period = styled.small`
  ${({ theme }) => theme.typography.common.default};
`;

const S = {
  PageLayout,
  Wrapper,
  Header,
  Title,
  PeriodContainer,
  Period,
};

export default S;
