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

interface DropdownProps {
  processNameList: string[];
}

export default function Dropdown({ processNameList }: DropdownProps) {
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
          {processNameList.map((processName) => (
            <S.DropdownListItem
              key={processName}
              onClick={() => handleSelect(processName)}
            >
              {processName}
            </S.DropdownListItem>
          ))}
        </S.DropdownList>
      )}
    </S.DropdownContainer>
  );
}
