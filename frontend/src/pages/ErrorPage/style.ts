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
`;

const StatusCode = styled.h1`
  ${({ theme }) => theme.typography.common.code};
  font-size: 12rem;
  line-height: 12rem;
  font-weight: 700;

  color: ${({ theme }) => theme.colors.brand.primary};
`;

const Title = styled.h2`
  ${({ theme }) => theme.typography.heading[900]};
`;

const Description = styled.p`
  ${({ theme }) => theme.typography.common.large};
  white-space: pre-line;
  line-height: 3rem; // 3rem = line-height of 2rem + paragraph spacing of 1rem
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
