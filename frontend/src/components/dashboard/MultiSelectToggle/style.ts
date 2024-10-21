import styled from '@emotion/styled';

const Wrapper = styled.div`
  position: relative;
  display: contents;
`;

const ToggleWrapper = styled.div<{ isVisible: boolean }>`
  display: flex;
  gap: 1.2rem;
  align-items: center;
  transition: transform 0.3s ease-in-out;
  transform: ${({ isVisible }) => (isVisible ? 'translateX(0)' : 'translateX(-14.6rem)')};
`;

const ToggleLabel = styled.span`
  ${({ theme }) => theme.typography.heading[200]};
  color: ${({ theme }) => theme.baseColors.grayscale[800]};
`;

const DropdownWrapper = styled.div<{ isVisible: boolean; isDisabled: boolean }>`
  position: absolute;

  opacity: ${({ isVisible }) => (isVisible ? 1 : 0.01)};
  visibility: ${({ isVisible }) => (isVisible ? 'visible' : 'hidden')};
  transition:
    opacity 0.3s ease-in-out,
    visibility 0.3s ease-in-out;

  color: ${({ theme }) => theme.baseColors.grayscale[800]};

  & .dropdown-header {
    padding: 4px 4px 4px 8px;
    color: ${({ theme, isDisabled }) => (isDisabled ? 'inherit' : theme.baseColors.grayscale[900])};
    background-color: ${({ theme, isDisabled }) => (isDisabled ? 'inherit' : theme.baseColors.grayscale[50])};
  }
`;

const S = {
  Wrapper,
  ToggleWrapper,
  ToggleLabel,
  DropdownWrapper,
};

export default S;
