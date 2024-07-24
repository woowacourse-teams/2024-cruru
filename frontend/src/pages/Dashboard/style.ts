import styled from '@emotion/styled';
import { hiddenStyles, hideScrollBar, visibleStyles } from '@styles/utils';

const AppContainer = styled.div`
  width: 100vw;
  height: 100vh;
  padding: 20px;
`;

const DashboardPanel = styled.div<{ isVisible: boolean }>`
  overflow-x: scroll;
  ${hideScrollBar};

  will-change: transform;
  transition: transform 0.2s ease-in-out;
  transform: translateY(${({ isVisible }) => (isVisible ? '0' : '1rem')});

  ${({ isVisible }) => (isVisible ? visibleStyles : hiddenStyles)};
`;

const S = {
  AppContainer,
  DashboardPanel,
};

export default S;
