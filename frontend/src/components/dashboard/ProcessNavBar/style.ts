import styled from '@emotion/styled';

const NavBar = styled.nav`
  height: 4rem;
  display: flex;
  align-items: center;
  padding: 3rem 1rem;
  border-bottom: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const NavItemList = styled.ul`
  width: 100%;
  list-style-type: none;

  display: flex;
  flex-direction: row;
  gap: 1.2rem;
`;

const NavItem = styled.li`
  padding: 0.4rem 0;
`;

const NavButton = styled.button<{ isCurrent: boolean }>`
  ${({ theme }) => theme.typography.heading[500]};
  color: ${({ isCurrent, theme }) => (isCurrent ? theme.baseColors.grayscale[950] : theme.baseColors.grayscale[500])};

  cursor: pointer;
  padding: 0.5rem 0;
  transition: color 0.3s ease;

  &:hover {
    color: ${({ theme }) => theme.baseColors.grayscale[950]};
  }
`;

const S = {
  NavBar,
  NavItem,
  NavItemList,
  NavButton,
};

export default S;
