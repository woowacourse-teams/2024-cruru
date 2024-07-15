import { useState } from 'react';
import S from './style';

export default function Dropdown() {
  const [isOpen, setIsOpen] = useState(false);
  const [selected, setSelected] = useState('단계');

  const handleToggle = () => setIsOpen(!isOpen);
  const handleSelect = (item: string) => {
    setSelected(item);
    setIsOpen(false);
  };

  return (
    <S.DropdownContainer>
      <S.DropdownButton onClick={handleToggle}>{selected}</S.DropdownButton>
      {isOpen && (
        <S.DropdownList>
          <S.DropdownListItem onClick={() => handleSelect('프로세스 1')}>프로세스 1</S.DropdownListItem>
          <S.DropdownListItem onClick={() => handleSelect('프로세스 2')}>프로세스 2</S.DropdownListItem>
          <S.DropdownListItem onClick={() => handleSelect('프로세스 3')}>프로세스 3</S.DropdownListItem>
          <S.DropdownListItem onClick={() => handleSelect('프로세스 4')}>프로세스 4</S.DropdownListItem>
        </S.DropdownList>
      )}
    </S.DropdownContainer>
  );
}
