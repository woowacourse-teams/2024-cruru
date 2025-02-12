import { useState } from 'react';
import { HiChevronDown } from 'react-icons/hi2';
import { Email } from '@customTypes/email';
import S from './style';

interface EmailHistoryItemProps {
  email: Email;
}

export default function EmailHistoryItem({ email }: EmailHistoryItemProps) {
  const [isOpen, setIsOpen] = useState(false);
  const { subject, createdDate, isSucceed, content, optimistic } = email;

  const handleClick = () => {
    setIsOpen(!isOpen);
  };

  const formattedDate = new Date(createdDate).toLocaleDateString('ko-KR', { month: 'long', day: 'numeric' });
  const formattedTime = new Date(createdDate).toLocaleTimeString('ko-KR', { hour: 'numeric', minute: 'numeric' });
  const status = optimistic ? '전송중' : isSucceed ? '발송완료' : '발송실패';

  return (
    <S.Container>
      <S.Header onClick={handleClick}>
        <S.Title>{subject}</S.Title>
        <S.RightSide>
          <S.Date>{`${formattedDate} / ${formattedTime}`}</S.Date>
          <S.Status status={status}>{status}</S.Status>
          <S.ArrowIcon isOpen={isOpen}>
            <HiChevronDown />
          </S.ArrowIcon>
        </S.RightSide>
      </S.Header>
      <S.Content isOpen={isOpen}>{content}</S.Content>
    </S.Container>
  );
}
