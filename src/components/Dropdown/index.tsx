import { useState } from 'react';
import S from './style';
import ChevronButton from '../ChevronButton';

interface DropdownProps {
  processNameList: string[];
}

export default function Dropdown({ processNameList }: DropdownProps) {
  const [isOpen, setIsOpen] = useState(false);
  const [selected, setSelected] = useState('단계');

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
          {processNameList.map((processName) => (
            <S.DropdownListItem onClick={() => handleSelect(processName)}>{processName}</S.DropdownListItem>
          ))}
        </S.DropdownList>
      )}
    </S.DropdownContainer>
  );
}
