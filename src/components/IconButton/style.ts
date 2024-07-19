import styled from '@emotion/styled';
import { Theme, css } from '@emotion/react';

type IconButtonSize = 'sm' | 'md' | 'lg';
type IconButtonShape = 'round' | 'square';

export interface IconButtonStyleProps {
  size?: IconButtonSize;
  shape?: IconButtonShape;
  outline?: boolean;
}

const shapeStyles = (shape: IconButtonShape = 'round') => css`
  border-radius: ${shape === 'round' ? '50%' : '0.8rem'};
`;

const sizeStyles = (size: IconButtonSize = 'sm') => css`
  width: ${size === 'sm' ? '2.4rem' : size === 'md' ? '3.6rem' : '4.8rem'};
  height: ${size === 'sm' ? '2.4rem' : size === 'md' ? '3.6rem' : '4.8rem'};
`;

const outlineStyles = (theme: Theme) => css`
  border: 1px solid ${theme.baseColors.grayscale[500]};
`;

const IconButton = styled.button<IconButtonStyleProps>`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};

  ${({ shape }) => shapeStyles(shape)}
  ${({ size }) => sizeStyles(size)}
  ${({ outline, theme }) => outline && outlineStyles(theme)}

  &:hover {
    background-color: ${({ theme }) => theme.colors.hover.bg};
    border: ${({ outline, theme }) => outline && `1px solid ${theme.colors.hover.border[100]}`};
  }

  img,
  svg {
    width: 100%;
    height: 100%;
  }
`;

const S = {
  IconButton,
};

export default S;
