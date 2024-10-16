import styled from '@emotion/styled';
import { hiddenStyles, hideScrollBar, visibleStyles } from '@styles/utils';

const AppContainer = styled.div`
  padding: 3.2rem 1.6rem;
  height: 100%;
`;

const DashboardPanel = styled.div<{ isVisible: boolean }>`
  width: 100%;
  height: 80%;
  padding: 2rem;

  overflow-x: scroll;
  ${hideScrollBar};

  transition: transform 0.2s ease-in-out;
  transform: translateY(${({ isVisible }) => (isVisible ? '0' : '1rem')});

  ${({ isVisible }) => (isVisible ? visibleStyles : hiddenStyles)};
`;

const S = {
  AppContainer,
  DashboardPanel,
};

export default S;
