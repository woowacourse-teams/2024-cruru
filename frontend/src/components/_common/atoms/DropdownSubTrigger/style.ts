import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const Item = styled.div<{ isHighlight: boolean; size: 'sm' | 'md'; hasSeparate: boolean }>`
  position: relative;
  display: block;
  width: 100%;

  display: flex;
  align-items: center;
  justify-content: space-between;

  padding: ${({ size }) => (size === 'md' ? '6px 24px' : '6px 12px')};
  ${({ theme, size }) => (size === 'md' ? theme.typography.common.default : theme.typography.common.small)};

  color: ${({ isHighlight, theme }) => (isHighlight ? theme.baseColors.redscale[500] : 'none')};
  border-top: ${({ theme, hasSeparate }) =>
    hasSeparate ? `0.15rem solid ${theme.baseColors.grayscale[400]}` : 'none'};

  cursor: pointer;
  transition: all 0.2s ease-in-out;

  white-space: nowrap;
  text-overflow: ellipsis;

  &:hover {
    background-color: ${({ isHighlight, theme }) =>
      isHighlight ? theme.baseColors.redscale[300] : theme.baseColors.grayscale[300]};
    color: ${({ isHighlight, theme }) => (isHighlight ? theme.baseColors.grayscale[50] : 'none')};
  }
`;

const fadeIn = keyframes`
  0% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
`;

const SubItemBoundary = styled.div<{ width: number; placement: 'right' | 'left' }>`
  position: absolute;
  padding: 0.4rem;

  top: -0.36rem;
  right: ${({ width, placement }) => (placement === 'left' ? `${width - 4}px` : `-${width + 4}px`)};

  display: flex;
  justify-content: center;
  align-items: center;

  animation: ${fadeIn} 0.3s ease-in-out;
`;

const SubItemContainer = styled.div<{ size: 'sm' | 'md'; width: number }>`
  width: ${({ width }) => `${width}px`};
  padding: ${({ size }) => (size === 'md' ? '16px 0px' : '8px 0px')};
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};

  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 8px;
`;

const S = {
  Item,
  SubItemBoundary,
  SubItemContainer,
};

export default S;
