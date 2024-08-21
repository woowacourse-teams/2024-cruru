import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

export interface StyleProps {
  width?: number;
  color?: 'white' | 'primary';
}

const bounceDelay = keyframes`
  0%, 80%, 100% { 
    transform: scale(0);
  } 
  40% { 
    transform: scale(1.0);
  }
`;

const Spinner = styled.div<StyleProps>`
  --design-width: ${({ width }) => (width ? `${width}px` : '100%')};
  --design-height: ${({ width }) => (width ? `${width * 0.23}px` : '1.8rem')};
  --design-color: ${({ theme, color }) =>
    color === 'primary' ? theme.colors.brand.primary : theme.baseColors.grayscale[50]};

  display: flex;
  justify-content: center;
  gap: ${({ width }) => (width ? `${width * 0.15}px` : '1.2rem')};
  width: var(--design-width);
`;

const Bounce = styled.div`
  width: var(--design-height);
  aspect-ratio: 1/1;

  background-color: var(--design-color);
  border-radius: 100%;
  animation: ${bounceDelay} 1.4s infinite ease-in-out both;

  &.bounce1 {
    animation-delay: -0.32s;
  }

  &.bounce2 {
    animation-delay: -0.16s;
  }
`;

const S = {
  Spinner,
  Bounce,
};

export default S;
