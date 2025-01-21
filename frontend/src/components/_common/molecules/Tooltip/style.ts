import styled from '@emotion/styled';
import { TooltipPlacement } from './types';

interface ContentContainerProps {
  placement: TooltipPlacement;
  zIndex?: number;
}

const ContentContainer = styled.div<ContentContainerProps>`
  position: absolute;
  z-index: ${({ zIndex }) => zIndex || 9999};

  padding: 0.8rem 1.2rem;
  background: ${({ theme }) => theme.baseColors.grayscale[900]};
  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.1);
  border-radius: 0.8rem;
  opacity: 0;
  pointer-events: none;

  ${({ theme }) => theme.typography.common.block};
  color: ${({ theme }) => theme.baseColors.grayscale[100]};
  line-height: inherit;

  transition: opacity 0.3s ease-in-out;

  &[data-visible='true'] {
    opacity: 0.99;
    pointer-events: auto;
  }
`;

const TooltipTriggerWrapper = styled.div`
  display: inline-block;
  position: relative;
`;

const S = {
  ContentContainer,
  TooltipTriggerWrapper,
};

export default S;
