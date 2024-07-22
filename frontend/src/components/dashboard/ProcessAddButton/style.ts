import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  align-items: flex-start;
`;

const HorizontalLine = styled.div`
  width: 2.4rem;
  height: 0.2rem;

  margin-top: 1.2rem;

  background-color: ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const S = {
  Container,
  HorizontalLine,
};

export default S;
