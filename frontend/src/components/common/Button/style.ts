import { css, SerializedStyles, Theme } from '@emotion/react';
import styled from '@emotion/styled';

export type ButtonSize = 'sm' | 'md' | 'fillContainer';
export type ButtonColor = 'white' | 'black' | 'primary' | 'secondary' | 'error';

export interface ButtonStyleProps {
  size: ButtonSize;
  color: ButtonColor;
}

const commonStyles = (theme: Theme) => css`
  display: flex;
  align-items: center;
  justify-content: center;

  border-radius: 0.8rem;
  ${theme.typography.common.default};
  white-space: nowrap;

  transition: all 0.2s ease-in-out;

  &:hover {
    cursor: pointer;
  }
`;

const sizeStyles = {
  sm: (theme: Theme) => css`
    ${theme.typography.common.small}
    padding: 0.8rem 0.4rem;
  `,
  md: (theme: Theme) => css`
    ${theme.typography.common.default}
    padding: 1.2rem;
  `,
  fillContainer: (theme: Theme) => css`
    ${theme.typography.common.default}
    width: 100%;
    height: 100%;
  `,
};

const colorStyles: { [key in ButtonColor]: (theme: Theme) => SerializedStyles } = {
  white: (theme: Theme) => css`
    color: ${theme.colors.text.default};
    background-color: ${theme.baseColors.grayscale[50]};
    border: 1px solid ${theme.baseColors.grayscale[500]};

    &:hover {
      background-color: ${theme.baseColors.grayscale[300]};
      border: 1px solid ${theme.baseColors.grayscale[800]};
    }
  `,
  black: (theme: Theme) => css`
    color: ${theme.baseColors.grayscale[50]};
    background-color: ${theme.baseColors.grayscale[900]};
    border: 1px solid ${theme.baseColors.grayscale[900]};

    &:hover {
      background-color: ${theme.baseColors.grayscale[950]};
      border: 1px solid ${theme.baseColors.grayscale[950]};
    }
  `,
  primary: (theme: Theme) => css`
    color: ${theme.baseColors.grayscale[50]};
    background-color: ${theme.baseColors.purplescale[500]};
    border: 1px solid ${theme.baseColors.purplescale[500]};

    &:hover {
      background-color: ${theme.baseColors.purplescale[700]};
      border: 1px solid ${theme.baseColors.purplescale[700]};
    }
  `,
  secondary: (theme: Theme) => css`
    color: ${theme.colors.text.default};
    background-color: ${theme.baseColors.purplescale[50]};
    border: 1px solid ${theme.baseColors.purplescale[500]};

    &:hover {
      background-color: ${theme.baseColors.purplescale[200]};
      border: 1px solid ${theme.baseColors.purplescale[700]};
    }
  `,
  error: (theme: Theme) => css`
    color: ${theme.colors.feedback.error};
    background-color: ${theme.baseColors.grayscale[50]};
    border: 1px solid ${theme.colors.feedback.error};

    &:hover {
      color: ${theme.baseColors.grayscale[50]};
      background-color: ${theme.colors.feedback.error};
      border: 1px solid ${theme.colors.feedback.error};
    }
  `,
};

const Button = styled.button<ButtonStyleProps>`
  ${({ theme }) => commonStyles(theme)}
  ${({ size, theme }) => sizeStyles[size](theme)}
  ${({ color, theme }) => colorStyles[color](theme)} 
  
  &:disabled {
    cursor: not-allowed;

    color: ${({ theme }) => theme.baseColors.grayscale[600]};
    background-color: ${({ theme }) => theme.baseColors.grayscale[300]};
    border: 1px solid ${({ theme }) => theme.baseColors.grayscale[300]};
  }
`;

const S = {
  Button,
};

export default S;
