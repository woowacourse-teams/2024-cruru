import styled from '@emotion/styled';
import { hiddenStyles, hideScrollBar, visibleStyles } from '@styles/utils';

const Nav = styled.nav`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  padding: 1.6rem 0;
  border-bottom: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const Tabs = styled.ul`
  width: 100%;
  list-style-type: none;

  display: flex;
  flex-direction: row;
  gap: 2.8rem;
`;

const Tab = styled.li`
  position: relative;

  &:not(:last-child)::after {
    content: '•';
    width: 2.8rem;
    text-align: center;

    position: absolute;
    transform: translateY(75%);
    color: ${({ theme }) => theme.baseColors.grayscale[500]};
  }
`;

const TabButton = styled.button<{ isActive: boolean }>`
  ${({ theme }) => theme.typography.heading[500]};
  color: ${({ isActive, theme }) => (isActive ? theme.baseColors.grayscale[950] : theme.baseColors.grayscale[500])};

  cursor: pointer;
  transition: color 0.3s ease;

  &:hover {
    color: ${({ theme }) => theme.baseColors.grayscale[950]};
  }

  &:disabled {
    color: ${({ theme }) => theme.baseColors.grayscale[500]};
    cursor: not-allowed;
  }
`;

const TabPanel = styled.div<{ isVisible: boolean }>`
  width: 100%;
  height: 85%;
  padding: 2rem 0;

  overflow-y: scroll;
  ${hideScrollBar};

  transition: transform 0.2s ease-in-out;
  transform: translateY(${({ isVisible }) => (isVisible ? '0' : '1rem')});

  ${({ isVisible }) => (isVisible ? visibleStyles : hiddenStyles)};
`;

const S = {
  Nav,
  Tabs,
  Tab,
  TabButton,
  TabPanel,
};

export default S;
