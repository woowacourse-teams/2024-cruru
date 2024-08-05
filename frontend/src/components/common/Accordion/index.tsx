import React, { useState } from 'react';
import { HiOutlineClipboardList } from 'react-icons/hi';

import S from './style';
import ChevronButton from '../ChevronButton';

interface AccordionProps {
  title: string;
  children: React.ReactNode;
}

function Accordion({ title, children }: AccordionProps) {
  const [isOpen, setIsOpen] = useState(false);

  const toggleAccordion = () => {
    setIsOpen(!isOpen);
  };

  return (
    <S.Container>
      <S.Header onClick={toggleAccordion}>
        <S.Title>
          <HiOutlineClipboardList />
          <S.TitleText>{title}</S.TitleText>
        </S.Title>
        <ChevronButton
          size="sm"
          direction={isOpen ? 'down' : 'up'}
        />
      </S.Header>
      {isOpen && <S.List>{children}</S.List>}
    </S.Container>
  );
}

Accordion.List = S.List;
Accordion.ListItem = S.ListItem;

export default Accordion;
