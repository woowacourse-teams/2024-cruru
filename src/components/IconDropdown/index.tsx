import IconButton from '@components/IconButton';
import { PropsWithChildren, useState } from 'react';
import S from './style';

interface IItem {
  name: string;
  onClick: () => void;
}

interface IIconDropdownProps extends PropsWithChildren {
  items: IItem[];
}

export default function IconDropdown({ children, items }: IIconDropdownProps) {
  const [isOpen, setIsOpen] = useState(false);

  const handleToggle = () => setIsOpen(!isOpen);
  const handleSelect = (item: IItem) => {
    item.onClick();
    setIsOpen(false);
  };

  return (
    <S.DropdownContainer>
      <IconButton
        onClick={handleToggle}
        size="sm"
      >
        {children}
      </IconButton>
      {isOpen && (
        <S.DropdownList>
          {items.map((item) => (
            <S.DropdownListItem
              key={item.name}
              onClick={() => handleSelect(item)}
            >
              {item.name}
            </S.DropdownListItem>
          ))}
        </S.DropdownList>
      )}
    </S.DropdownContainer>
  );
}
