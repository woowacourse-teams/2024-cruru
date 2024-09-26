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

const rotate = keyframes`
  0% { transform: rotate(0deg) scale(0.8); }
  50% { transform: rotate(360deg) scale(1.2); }
  100% { transform: rotate(720deg) scale(0.8); }
`;

const ball1 = keyframes`
  0% {
    box-shadow: 3rem 0 0 #ff3d00;
  }
  50% {
    box-shadow: 0 0 0 #ff3d00;
    margin-bottom: 0;
    transform: translate(1.5rem, 1.5rem);
  }
  100% {
    box-shadow: 3rem 0 0 #ff3d00;
    margin-bottom: 1rem;
  }
`;

const ball2 = keyframes`
  0% {
    box-shadow: 3rem 0 0 #fff;
  }
  50% {
    box-shadow: 0 0 0 #fff;
    margin-top: -2rem;
    transform: translate(1.5rem, 1.5rem);
  }
  100% {
    box-shadow: 3rem 0 0 #fff;
    margin-top: 0;
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
  animation: ${rotate} 1s infinite;
  height: 5rem;
  width: 5rem;

  &:before,
  &:after {
    border-radius: 50%;
    content: '';
    display: block;
    height: 2rem;
    width: 2rem;
  }

  &:before {
    animation: ${ball1} 1s infinite;
    background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
    box-shadow: 3rem 0 0 #ff3d00;
    margin-bottom: 1rem;
  }

  &:after {
    animation: ${ball2} 1s infinite;
    background-color: ${({ theme }) => theme.colors.brand.primary};
    box-shadow: 2rem 0 0 #fff;
  }
`;

const S = {
  Spinner,
  Bounce,
  PageLoader,
};

export default S;
