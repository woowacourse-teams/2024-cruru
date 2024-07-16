import { useState } from 'react';
import S from './style';
import ChevronButton from '../ChevronButton';

interface DropdownProps {
  defaultSelected: string;
  items: string[];
}

export default function Dropdown({ defaultSelected, items }: DropdownProps) {
  const [isOpen, setIsOpen] = useState(false);
  const [selected, setSelected] = useState(defaultSelected);

  const handleToggle = () => setIsOpen(!isOpen);
  const handleSelect = (item: string) => {
    setSelected(item);
    setIsOpen(false);
  };

  return (
    <S.DropdownContainer>
      <S.DropdownButton onClick={handleToggle}>
        {selected}
        <ChevronButton
          direction="down"
          size="sm"
        />
      </S.DropdownButton>
      {isOpen && (
        <S.DropdownList>
          {items.map((item) => (
            <S.DropdownListItem
              key={item}
              onClick={() => handleSelect(item)}
            >
              {item}
            </S.DropdownListItem>
          ))}
        </S.DropdownList>
      )}
    </S.DropdownContainer>
  );
}
