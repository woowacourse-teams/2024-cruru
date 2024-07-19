import S from './style';
import DropdownItem from '../DropdownItem';

type Item = {
  id: number;
  name: string;
  isHighlight?: boolean;
  onClick: () => void;
};

export interface PopOverMenuProps {
  isOpen: boolean;
  size?: 'sm' | 'md';
  items: Item[];
}

export default function PopOverMenu({ isOpen, size = 'sm', items }: PopOverMenuProps) {
  return (
    <S.Container
      size={size}
      isOpen={isOpen}
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
