import chevronDown from '@assets/images/chevronDown.svg';
import { useState } from 'react';
import S from './style';

interface IItem {
  name: string;
  onClick: () => void;
}

interface DropdownProps {
  defaultSelected: string;
  items: IItem[];
}

export default function Dropdown({ defaultSelected, items }: DropdownProps) {
  const [isOpen, setIsOpen] = useState(false);
  const [selected, setSelected] = useState(defaultSelected);

  const handleToggle = () => setIsOpen(!isOpen);
  const handleSelect = (item: IItem) => {
    setSelected(item.name);
    item.onClick();
    setIsOpen(false);
  };

  return (
    <S.DropdownContainer>
      <S.DropdownButton onClick={handleToggle}>
        {selected}
        <img
          src={chevronDown}
          alt="chevron"
        />
      </S.DropdownButton>
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
