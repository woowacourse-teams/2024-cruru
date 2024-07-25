import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  padding: 1.6rem;
  border-radius: 0.8rem;

  height: 100%;

  & > div:first-child {
    border-bottom: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[600]};
  }
`;

const S = {
  Container,
};

export default S;
