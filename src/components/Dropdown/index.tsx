import { useState } from 'react';
import chevronDown from '@assets/images/chevronDown.svg';
import S from './style';

interface DropdownProps {
  defaultSelectedValue: string;
  processNameList: string[];
}

export default function Dropdown({ defaultSelectedValue, processNameList }: DropdownProps) {
  const [isOpen, setIsOpen] = useState(false);
  const [selected, setSelected] = useState(defaultSelectedValue);

  const handleToggle = () => setIsOpen(!isOpen);
  const handleSelect = (item: string) => {
    setSelected(item);
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
