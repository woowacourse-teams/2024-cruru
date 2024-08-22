import styled from '@emotion/styled';

const Wrapper = styled.div`
  width: 100vw;
  height: 100vh;

  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 3.2rem;

  padding: 0 16rem;
  color: ${({ theme }) => theme.colors.text.default};

  @media (max-width: ${({ theme }) => theme.breakpoints.tablet}) {
    padding: 0 4rem;
    gap: 2rem;
  }

  @media (max-width: ${({ theme }) => theme.breakpoints.mobile}) {
    padding: 0 2.4rem;
    gap: 1.6rem;
  }
`;

const StatusCode = styled.h1`
  ${({ theme }) => theme.typography.common.code};
  font-size: 12rem;
  line-height: 12rem;
  font-weight: 700;

  color: ${({ theme }) => theme.colors.brand.primary};

  @media (max-width: ${({ theme }) => theme.breakpoints.tablet}) {
    font-size: 10rem;
    line-height: 10rem;
  }

  @media (max-width: ${({ theme }) => theme.breakpoints.mobile}) {
    font-size: 8rem;
    line-height: 8rem;
  }
`;

const Title = styled.h2`
  ${({ theme }) => theme.typography.heading[900]};

  @media (max-width: ${({ theme }) => theme.breakpoints.tablet}) {
    ${({ theme }) => theme.typography.heading[800]};
  }

  @media (max-width: ${({ theme }) => theme.breakpoints.mobile}) {
    ${({ theme }) => theme.typography.heading[600]};
  }
`;

const Description = styled.p`
  ${({ theme }) => theme.typography.common.large};
  white-space: pre-line;
  line-height: 3rem; // 3rem = line-height of 2rem + paragraph spacing of 1rem

  @media (max-width: ${({ theme }) => theme.breakpoints.tablet}) {
    ${({ theme }) => theme.typography.common.default};
    line-height: 2.4rem; // 3rem = line-height of 1.4 + paragraph spacing of 1rem
  }

  @media (max-width: ${({ theme }) => theme.breakpoints.mobile}) {
    ${({ theme }) => theme.typography.common.small};
    line-height: 2rem; // 3rem = line-height of 1.2 + paragraph spacing of 0.8rem
  }
`;

const ButtonContainer = styled.div`
  display: flex;
  gap: 1.6rem;
`;

const S = {
  Wrapper,
  StatusCode,
  Title,
  Description,
  ButtonContainer,
};

export default S;
