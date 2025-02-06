import styled from '@emotion/styled';

const SidebarHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 2.4rem;
`;

const Logo = styled.img`
  height: 2.4rem;
`;

const SidebarToggleIcon = styled.div`
  color: ${({ theme }) => theme.baseColors.grayscale[500]};
`;

const S = {
  SidebarHeader,
  Logo,
  SidebarToggleIcon,
};

export default S;
