import styled from '@emotion/styled';

const SidebarItemLink = styled.div<{ isSelected: boolean; isSidebarOpen?: boolean }>`
  display: flex;
  align-items: center;
  justify-content: ${({ isSidebarOpen }) => (isSidebarOpen ? 'left' : 'center')};

  gap: 1rem;
  color: ${({ theme }) => theme.baseColors.grayscale[900]};
  opacity: ${({ isSelected }) => (isSelected ? 0.99 : 0.4)};

  transition: opacity 0.2s ease;

  cursor: pointer;

  &:hover {
    opacity: 0.99;
  }
`;

const SidebarItemText = styled.p`
  width: 21rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

  ${({ theme }) => theme.typography.common.largeBlock}
`;

const IconContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 24px;
  aspect-ratio: 1/1;
`;

const S = {
  SidebarItemLink,
  SidebarItemText,
  IconContainer,
};

export default S;
