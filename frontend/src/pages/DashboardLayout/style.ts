import styled from '@emotion/styled';

const LayoutBg = styled.div`
  min-height: 100vh;
  min-width: 100vw;

  padding: 2.4rem;
  background-color: ${({ theme }) => theme.baseColors.redscale[50]};

  display: flex;
`;

const Layout = styled.div`
  display: flex;
  flex: 1;
  border-radius: 1.6rem;
  overflow: hidden;

  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
`;

const MainContainer = styled.div`
  width: 80%;
  height: 100%;
  // INFO: 4.8rem = 바깥 컨테이너의 padding-top + padding-bottom
  max-height: calc(100vh - 4.8rem);
`;

const S = {
  LayoutBg,
  Layout,
  MainContainer,
};

export default S;
