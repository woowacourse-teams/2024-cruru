import styled from '@emotion/styled';

const Container = styled.div<{ isSidebarOpen: boolean }>`
  position: relative;
  width: ${({ isSidebarOpen }) => (isSidebarOpen ? '276px' : '56px')};
  height: 100%;
  border-right: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  padding: 3.6rem 1.6rem;

  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};

  display: flex;
  flex-direction: column;
  gap: 4rem;
`;

const SidebarHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 2.4rem;
`;

const Logo = styled.img`
  height: 2.4rem;
`;

const Contents = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const SidebarItem = styled.li`
  list-style: none;
`;

const SidebarItemLink = styled.div<{ isSelected: boolean }>`
  display: flex;
  align-items: center;
  gap: 1rem;
  color: ${({ theme }) => theme.baseColors.grayscale[900]};
  opacity: ${({ isSelected }) => (isSelected ? 0.99 : 0.4)};

  transition: opacity 0.2s ease;

  &:hover {
    opacity: 0.99;
  }
`;

const SidebarItemText = styled.p`
  width: 21rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

  ${({ theme }) => theme.typography.common.largeAccent}
`;

const Divider = styled.div`
  border-bottom: 0.15rem solid ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const ContentSubTitle = styled.div`
  height: 2rem;
  color: ${({ theme }) => theme.baseColors.grayscale[500]};
  ${({ theme }) => theme.typography.common.default}
`;

const Circle = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;

  &::before {
    content: '•';
    scale: 1.3;
  }
`;

const S = {
  Container,
  SidebarHeader,
  Logo,
  Contents,
  SidebarItem,
  SidebarItemLink,
  SidebarItemText,
  Divider,
  ContentSubTitle,
  Circle,
};

export default S;
