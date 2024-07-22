import React, { PropsWithChildren } from 'react';
import S, { ButtonStyleProps } from './style';

// INFO: 현재 Button 컴포넌트는 스타일 커스터마이징만 가능할 뿐 기능적인 부분은 없습니다.
// INFO: 함수형 컴포넌트만 Storybook 테스트가 가능해 일단 함수형으로 작성합니다.
// 2024.07.18 작성자: 렛서
export default function Button({
  children,
  onClick,
  type = 'button',
  size = 'md',
  color = 'white',
}: PropsWithChildren<React.ComponentProps<'button'> & ButtonStyleProps>) {
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
