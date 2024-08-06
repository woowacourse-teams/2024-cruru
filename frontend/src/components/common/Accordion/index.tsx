import React, { useState } from 'react';
import { HiOutlineClipboardList } from 'react-icons/hi';

import S from './style';
import ChevronButton from '../ChevronButton';

interface AccordionProps {
  title: string;
  children: React.ReactNode;
}

function Accordion({ title, children }: AccordionProps) {
  // TODO: 현재 아코디언의 오픈값을 True로 설정합니다. 추후에 아코디언이 추가될 경우 변경이 필요합니다.
  const [isOpen] = useState(true);

  const toggleAccordion = () => {
    // setIsOpen(!isOpen);
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

Accordion.ListItem = S.ListItem;

export default Accordion;
