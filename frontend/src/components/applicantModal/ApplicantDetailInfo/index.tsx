import AppDetailHeader from './AppDetailHeader';
import QuestionSection from './QuestionSection';
import S from './style';

interface ApplicantDetailInfoProps {
  applicantId: number;
}

export default function ApplicantDetailInfo({ applicantId }: ApplicantDetailInfoProps) {
  return (
    <S.Container>
      <AppDetailHeader
        headerTabs={[
          {
            id: 0,
            name: '지원서',
            onClick: () => console.log('지원서가 클릭되었습니다.'),
          },
          {
            id: 1,
            name: '이력서',
            onClick: () => console.log('이력서가 클릭되었습니다.'),
          },
        ]}
        activeTabId={0}
        content="지원 시 접수된 지원서 내용입니다."
      />
      <QuestionSection applicantId={applicantId} />
    </S.Container>
  );
}
