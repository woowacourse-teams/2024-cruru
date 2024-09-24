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

const eyeMove = keyframes`
  0%, 10% { background-position: 0px 0px; }
  13%, 40% { background-position: -15px 0px; }
  43%, 70% { background-position: 15px 0px; }
  73%, 90% { background-position: 0px 15px; }
  93%, 100% { background-position: 0px 0px; }
`;

const blink = keyframes`
  0%, 10%, 12%, 20%, 22%, 40%, 42%, 60%, 62%, 70%, 72%, 90%, 92%, 98%, 100% {
    height: 48px;
  }
  11%, 21%, 41%, 61%, 71%, 91%, 99% {
    height: 18px;
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

const PageLoader = styled.span`
  position: relative;
  width: 108px;
  display: flex;
  justify-content: space-between;

  &::before,
  &::after {
    content: '';
    display: inline-block;
    width: 48px;
    height: 48px;
    background-color: #fff;
    background-image: radial-gradient(circle 14px, #0d161b 100%, transparent 0);
    background-repeat: no-repeat;
    border-radius: 50%;
    animation:
      ${eyeMove} 10s infinite,
      ${blink} 10s infinite;
    border: 2px solid ${({ theme }) => theme.baseColors.grayscale[300]};
  }
`;

const S = {
  Spinner,
  Bounce,
  PageLoader,
};

export default S;
