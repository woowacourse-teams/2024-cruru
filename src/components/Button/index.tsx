import React, { PropsWithChildren } from 'react';
import S from './style';

export default function Button({
  children,
  onClick,
  type = 'button',
}: PropsWithChildren<React.ComponentProps<'button'>>) {
  return (
    <S.Button
      type={type}
      onClick={onClick}
    >
      {children}
    </S.Button>
  );
}
