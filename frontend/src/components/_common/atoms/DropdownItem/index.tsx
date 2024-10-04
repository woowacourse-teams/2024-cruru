import { useDropdown } from '@contexts/DropdownContext';
import S from './style';

export interface DropdownItemProps {
  item: string;
  size: 'sm' | 'md';
  onClick: () => void;
  isHighlight?: boolean;
  hasSeparate?: boolean;
}

export default function DropdownItem({
  item,
  size,
  onClick,
  isHighlight = false,
  hasSeparate = false,
}: DropdownItemProps) {
  const { close, setSelected } = useDropdown();

  const clickHandler = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    onClick();
    close();
    setSelected(item);
  };

  return (
    <S.Item
      size={size}
      onClick={clickHandler}
      isHighlight={isHighlight}
      hasSeparate={hasSeparate}
    >
      {item}
    </S.Item>
  );
}
