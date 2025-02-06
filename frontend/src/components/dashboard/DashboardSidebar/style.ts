import styled from '@emotion/styled';
import { hideScrollBar } from '@styles/utils';

const Container = styled.div<{ isSidebarOpen: boolean }>`
  position: relative;
  width: ${({ isSidebarOpen }) => (isSidebarOpen ? '276px' : '56px')};
  height: 100%;
  border-right: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  padding: 3.6rem 1.6rem 6.4rem;

  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};

  display: flex;
  flex-direction: column;
  gap: 4rem;
`;

const SidebarNav = styled.nav`
  flex: 1;
  overflow: hidden;
`;

const Contents = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  max-height: 100%;
`;

const SidebarScrollBox = styled.li`
  overflow-y: scroll;
  ${hideScrollBar};
`;

const SidebarItemGroup = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  margin-bottom: 2.4rem;
`;

const SidebarItem = styled.li<{ isSidebarOpen: boolean }>`
  display: flex;
  align-items: center;

  list-style: none;
  height: 2.4rem;
`;

const Divider = styled.div`
  border-bottom: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const ContentSubTitle = styled.div`
  height: 2rem;
  color: ${({ theme }) => theme.baseColors.grayscale[500]};
  ${({ theme }) => theme.typography.common.smallBlock}

  margin-bottom: -0.6rem;
`;

const Circle = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;

  &::before {
    content: '•';
    scale: 1;
  }
`;

const S = {
  Container,
  Contents,
  SidebarNav,
  SidebarScrollBox,
  SidebarItemGroup,
  SidebarItem,
  Divider,
  ContentSubTitle,
  Circle,
};

export default S;
