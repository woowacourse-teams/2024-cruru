import styled from '@emotion/styled';

const Layout = styled.div`
  min-height: 100vh;
  min-width: 100vw;

  padding: 6rem 7.2rem;

  display: flex;
  flex: 1;
  gap: 4rem;
  overflow: hidden;
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
`;

const MainContainer = styled.div`
  flex: 1;
`;

const S = {
  Layout,
  MainContainer,
};

export default S;
