import styled from '@emotion/styled';

const Container = styled.div`
  padding: 1.6rem;
  background-color: ${({ theme }) => theme.colors.text.block};

  width: 100%;
  height: 100%;
  overflow-y: scroll;

  scrollbar-width: none; /* 파이어폭스 */
  &::-webkit-scrollbar {
    display: none;
  }
`;

const InnerContainer = styled.div`
  width: 100%;
  min-height: 100%;

  border-radius: 1.6rem;
  padding: 2.4rem 3.6rem;
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
`;

const S = {
  Container,
  InnerContainer,
};

export default S;
