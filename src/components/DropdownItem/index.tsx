import S from './style';

export interface DropdownItemProps {
  item: string;
  size: 'sm' | 'md';
  onClick: () => void;
  isHighlight?: boolean;
}

export default function DropdownItem({ item, size, onClick, isHighlight = false }: DropdownItemProps) {
  return (
    <S.Item
      size={size}
      onClick={onClick}
      isHighlight={isHighlight}
    >
      {item}
    </S.Item>
  );
}
