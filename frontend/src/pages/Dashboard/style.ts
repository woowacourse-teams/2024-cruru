import styled from '@emotion/styled';
import { hiddenStyles, hideScrollBar, visibleStyles } from '@styles/utils';

const AppContainer = styled.div`
  padding: 20px;
`;

const DashboardPanel = styled.div<{ isVisible: boolean }>`
  width: 100%;
  height: 80%;
  padding: 20px;

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
