import styled from '@emotion/styled';

const IconSizeMap = {
  lg: '4.8rem',
  md: '3.6rem',
  sm: '2.4rem',
};

export type ButtonType = 'label' | 'icon';
export type ButtonSize = 'sm' | 'md' | 'lg';

interface ButtonStyleProps {
  buttonType?: ButtonType;
  size: ButtonSize;
  outline?: boolean;
  borderRadius?: string;
}

const Button = styled.button<ButtonStyleProps>`
  display: flex;
  align-items: center;

  border: ${({ outline, theme }) => (outline ? `1px solid ${theme.colors.grayscale[400]}` : 'none')};
  border-radius: ${({ borderRadius }) => borderRadius};

  padding: ${({ buttonType }) => (buttonType === 'icon' ? '0.4rem' : '0.8rem 1.6rem')};

  background-color: ${({ theme }) => theme.colors.grayscale[50]};

  &:hover {
    cursor: pointer;
    background-color: ${({ theme }) => theme.colors.grayscale[200]};
  }
`;

const IconWrapper = styled.div<ButtonStyleProps>`
  img {
    display: block;

    width: ${({ size }) => IconSizeMap[size]};
  }
`;

const S = {
  Button,
  IconWrapper,
};

export default S;
