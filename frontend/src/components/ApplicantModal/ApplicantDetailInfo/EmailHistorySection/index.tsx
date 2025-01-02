// import specificApplicant from '@hooks/useSpecificApplicant';
import Button from '@components/_common/atoms/Button';
import EmailHistoryItem from '@components/_common/atoms/EmailHistoryItem';
import S from './style';

interface EmailHistorySectionProps {
  applicantId: number;
}

const emailHistory = [
  {
    id: 1,
    title: '[크루루] 면접 안내 드립니다.',
    content: `안녕하세요. 크루루 인재 영입 담당자입니다.\n
축하드립니다. 서류 전형을 합격하셨습니다.
면접 일정 확인 및 안내드리니 확인 부탁드립니다.
- 1월 30일 오후 1시 30분.

다시 한 번 크루루에 지원해주셔서 감사합니다.

- 크루루 인재 영입 담당자 드림`,
    date: '2024-12-17 12:30:00',
    isSucceed: true,
  },
  {
    id: 2,
    title: '안녕하세요. 크루루입니다.',
    content: `안녕하세요. 크루루 인재 영입 담당자입니다.
먼저, 크루루에 관심을 갖고 지원해 주셔서 진심으로 감사드립니다.
내부적으로 신중히 검토한 결과, 아쉽게도 이번 기수에서는 귀하를 모시지 못하게 되었습니다.

항상 건강 유념하시고 행복하시길 바랍니다.
감사합니다.

- 진심을 담아, 크루루 인재 영입 담당자 드림`,
    date: '2024-01-01 13:0:00',
    isSucceed: false,
  },
];

export default function EmailHistorySection({ applicantId }: EmailHistorySectionProps) {
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
            key={email.id}
            title={email.title}
            content={email.content}
            date={email.date}
            isSucceed={email.isSucceed}
          />
        ))}
      </S.ContentContainer>
    </S.Container>
  );
}
