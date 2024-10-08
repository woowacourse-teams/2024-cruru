import type { PopOverMenuItem } from '@customTypes/common';
import DropdownItem from '@components/_common/atoms/DropdownItem';
import S from './style';

export interface PopOverMenuProps {
  isOpen: boolean;
  setClose: () => void;
  size?: 'sm' | 'md';
  popOverPosition?: string;
  items: PopOverMenuItem[];
}

export default function PopOverMenu({ isOpen, setClose, size = 'sm', popOverPosition, items }: PopOverMenuProps) {
  return (
    <S.Container
      size={size}
      isOpen={isOpen}
      popOverPosition={popOverPosition}
    >
      {isOpen && (
        <S.ListWrapper>
          <S.List size={size}>
            {items.map(({ name, isHighlight, id, hasSeparate, onClick }) => (
              <DropdownItem
                size={size}
                onClick={() => {
                  setClose();
                  onClick({ targetProcessId: id });
                }}
                key={id}
                item={name}
                isHighlight={isHighlight}
                hasSeparate={hasSeparate}
              />
            ))}
          </S.List>
        </S.ListWrapper>
      )}
    </S.Container>
  );
}
