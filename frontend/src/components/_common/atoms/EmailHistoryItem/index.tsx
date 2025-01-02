import { useState } from 'react';
import { HiChevronDown } from 'react-icons/hi2';
import S from './style';

interface EmailHistoryItemProps {
  title: string;
  content: string;
  date: string;
  isSucceed: boolean;
}

export default function EmailHistoryItem({ title, content, date, isSucceed }: EmailHistoryItemProps) {
  const [isOpen, setIsOpen] = useState(false);

  const handleClick = () => {
    setIsOpen(!isOpen);
  };

  const formattedDate = new Date(date).toLocaleDateString('ko-KR', { month: 'long', day: 'numeric' });
  const formattedTime = new Date(date).toLocaleTimeString('ko-KR', { hour: 'numeric', minute: 'numeric' });

  return (
    <S.Container>
      <S.Header onClick={handleClick}>
        <S.Title>{title}</S.Title>
        <S.RightSide>
          <S.Date>{`${formattedDate} / ${formattedTime}`}</S.Date>
          <S.Status isSucceed={isSucceed}>{isSucceed ? '발송 완료' : '발송 실패'}</S.Status>
          <S.ArrowIcon isOpen={isOpen}>
            <HiChevronDown />
          </S.ArrowIcon>
        </S.RightSide>
      </S.Header>
      <S.Content isOpen={isOpen}>{content}</S.Content>
    </S.Container>
  );
}
