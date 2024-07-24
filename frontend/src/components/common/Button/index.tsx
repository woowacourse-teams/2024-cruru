import React, { PropsWithChildren } from 'react';
import S, { ButtonStyleProps } from './style';

export type ButtonProps = PropsWithChildren<React.ComponentProps<'button'> & ButtonStyleProps>;

export default function Button({ children, onClick, type = 'button', size = 'md', color = 'white' }: ButtonProps) {
  return (
    <S.Button
      size={size}
      color={color}
      type={type}
      onClick={onClick}
    >
      {children}
    </S.Button>
  );
}
