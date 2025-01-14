import Button from '@components/_common/atoms/Button';
import EmailHistoryItem from '@components/_common/atoms/EmailHistoryItem';
import MessageForm, { SubmitProps } from '@components/dashboard/SideFloatingMessageForm/MessageForm';

import { useState } from 'react';
import useEmail from '@hooks/useEmail';
import useClubId from '@hooks/service/useClubId';
import useGetEmailHistory from '@hooks/useGetEmailHistory';

import S from './style';

interface EmailHistorySectionProps {
  applicantId: number;
}

export default function EmailHistorySection({ applicantId }: EmailHistorySectionProps) {
  const [toggle, setToggle] = useState(false);
  const { mutate: sendMutate, isPending } = useEmail(() => setToggle(false));
  const { emailHistory } = useGetEmailHistory({ applicantId });
  const clubId = useClubId().getClubId();

  const toggleHidden = () => setToggle((prev) => !prev);

  const handleSubmit = (props: SubmitProps) => {
    if (!isPending) sendMutate({ clubId, applicantIds: [applicantId], ...props });
  };

  return (
    <S.Container>
      <S.Header>
        <S.Title>{`보낸 메일 (${emailHistory.length})`}</S.Title>
        <S.EmailButtonContainer>
          <Button
            size="fillContainer"
            color="white"
            onClick={toggleHidden}
          >
            {!toggle ? '메일 쓰기' : '취소'}
          </Button>
        </S.EmailButtonContainer>
      </S.Header>
      {toggle && (
        <S.EmailFormContainer>
          <MessageForm>
            <MessageForm.Form
              onSubmit={handleSubmit}
              isPending={isPending}
            />
          </MessageForm>
        </S.EmailFormContainer>
      )}
      <S.ContentContainer>
        {emailHistory.map((email) => (
          <EmailHistoryItem
            key={`${email.createdDate}`}
            email={email}
          />
        ))}
      </S.ContentContainer>
    </S.Container>
  );
}
