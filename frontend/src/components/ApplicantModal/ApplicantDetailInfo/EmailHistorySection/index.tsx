import Button from '@components/_common/atoms/Button';
import EmailHistoryItem from '@components/_common/atoms/EmailHistoryItem';
import useGetEmailHistory from '@hooks/useGetEmailHistory';
import S from './style';

interface EmailHistorySectionProps {
  applicantId: number;
}

export default function EmailHistorySection({ applicantId }: EmailHistorySectionProps) {
  const { emailHistory } = useGetEmailHistory({ applicantId });

  return (
    <S.Container>
      <S.Header>
        <S.Title>{`보낸 메일 (${emailHistory.length})`}</S.Title>
        <Button
          size="sm"
          color="primary"
        >
          메일 쓰기
        </Button>
      </S.Header>
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
