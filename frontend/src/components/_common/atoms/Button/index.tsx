import React, { PropsWithChildren } from 'react';
import S, { ButtonStyleProps } from './style';

export type ButtonProps = PropsWithChildren<React.ComponentProps<'button'> & ButtonStyleProps>;

export default function Button({
  children,
  onClick,
  type = 'button',
  size = 'md',
  color = 'white',
  disabledColor = 'default',
  ...rest
}: ButtonProps) {
  return (
    <S.Button
      size={size}
      color={color}
      type={type}
      onClick={onClick}
      disabledColor={disabledColor}
      {...rest}
    >
      {children}
    </S.Button>
  );
}
