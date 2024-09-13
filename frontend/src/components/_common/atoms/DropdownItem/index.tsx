import S from './style';

export interface DropdownItemProps {
  item: string;
  size: 'sm' | 'md';
  onClick: () => void;
  isHighlight?: boolean;
}

export default function DropdownItem({ item, size, onClick, isHighlight = false }: DropdownItemProps) {
  const clickHandler = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    onClick();
  };

  return (
    <S.Item
      size={size}
      onClick={clickHandler}
      isHighlight={isHighlight}
    >
      {item}
    </S.Item>
  );
}
