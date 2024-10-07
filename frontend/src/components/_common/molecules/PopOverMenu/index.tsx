import { PropsWithChildren } from 'react';
import S from './style';

export interface PopOverMenuProps extends PropsWithChildren {
  isOpen: boolean;
  size?: 'sm' | 'md';
  popOverPosition?: string;
}

export default function PopOverMenu({ isOpen, size = 'sm', popOverPosition, children }: PopOverMenuProps) {
  return (
    <S.Container
      size={size}
      isOpen={isOpen}
      popOverPosition={popOverPosition}
    >
      {isOpen && (
        <S.ListWrapper>
          <S.List size={size}>{children}</S.List>
        </S.ListWrapper>
      )}
    </S.Container>
  );
}
