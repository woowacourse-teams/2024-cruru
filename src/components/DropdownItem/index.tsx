import S from './style';

export interface DropdownItemProps {
  item: string;
  onClick: () => void;
  isHighlight?: boolean;
}

export default function DropdownItem({ item, onClick, isHighlight = false }: DropdownItemProps) {
  return (
    <S.Item
      onClick={onClick}
      isHighlight={isHighlight}
    >
      {item}
    </S.Item>
  );
}
