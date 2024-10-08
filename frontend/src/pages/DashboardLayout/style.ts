import styled from '@emotion/styled';

interface SidebarStyleProps {
  isSidebarOpen: boolean;
}

const Layout = styled.div`
  height: 100vh;
  width: 100vw;
  display: flex;
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
`;

const SidebarContainer = styled.div`
  display: flex;
  height: 100%;
  width: fit-content;
`;

const Sidebar = styled.div``;

const SidebarController = styled.div<SidebarStyleProps>`
  width: ${({ isSidebarOpen }) => (isSidebarOpen ? '0px' : 'fit-content')};
  transform: ${({ isSidebarOpen }) => (isSidebarOpen ? 'translateX(-6rem)' : 'none')};
  z-index: 1;
`;

const ToggleButton = styled.div`
  padding: 3.3rem 0rem 0rem 1.6rem;
`;

const MainContainer = styled.div<SidebarStyleProps>`
  flex: 1;
  height: 100vh;

  padding-left: ${({ isSidebarOpen }) => isSidebarOpen && '1rem'};
`;

const S = {
  Layout,
  SidebarContainer,
  Sidebar,
  SidebarController,
  ToggleButton,
  MainContainer,
};

export default S;
