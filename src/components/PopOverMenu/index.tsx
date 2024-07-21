import S from './style';
import DropdownItem from '../DropdownItem';

import type { PopOverMenuItem } from '@/types/common';

export interface PopOverMenuProps {
  isOpen: boolean;
  size?: 'sm' | 'md';
  popOverPosition?: string;
  items: PopOverMenuItem[];
}

export default function PopOverMenu({ isOpen, size = 'sm', popOverPosition, items }: PopOverMenuProps) {
  return (
    <S.Container
      size={size}
      isOpen={isOpen}
      popOverPosition={popOverPosition}
    >
      {isOpen && (
        <S.List>
          {items.map(({ name, isHighlight, id, onClick }) => (
            <DropdownItem
              onClick={onClick}
              key={id}
              item={name}
              isHighlight={isHighlight}
            />
          ))}
        </S.List>
      )}
    </S.Container>
  );
}
