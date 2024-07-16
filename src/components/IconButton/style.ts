import styled from '@emotion/styled';
import { ButtonSize } from '../Button/style';

const IconSizeMap = {
  lg: '4.8rem',
  md: '3.6rem',
  sm: '2.4rem',
};

export interface IconButtonStyleProps {
  size: ButtonSize;
  outline?: boolean;
  borderRadius?: string;
}

const IconWrapper = styled.div<IconButtonStyleProps>`
  display: flex;
  align-items: center;

  padding: 0.4rem;
  border-radius: ${({ borderRadius }) => borderRadius};
  border: ${({ outline, theme }) => (outline ? `1px solid ${theme.colors.grayscale[300]}` : 'none')};

  img {
    display: block;
    width: ${({ size }) => IconSizeMap[size]};
  }

  &:hover {
    background-color: ${({ theme }) => theme.colors.grayscale[200]};
  }
`;

const S = {
  IconWrapper,
};

export default S;
