import React from 'react';
import S, { ButtonSize } from './style';

interface ButtonProps extends React.ComponentProps<'button'> {
  label?: string;
  icon?: React.ReactNode;
  size: ButtonSize;
  outline?: boolean;
  borderRadius?: string;
}

export default function Button({
  onClick,
  type = 'button',

  label,
  icon,
  size = 'lg',
  outline = false,
  borderRadius = '0.8rem',
}: ButtonProps) {
  return (
    <S.Button
      type={type}
      onClick={onClick}
      size={size}
      outline={outline}
      borderRadius={borderRadius}
      buttonType={icon ? 'icon' : 'label'}
    >
      {icon && <S.IconWrapper size={size}>{icon}</S.IconWrapper>}
      {label && label}
    </S.Button>
  );
}
