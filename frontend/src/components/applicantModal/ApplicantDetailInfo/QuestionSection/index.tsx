import QuestionBox from '@components/common/QuestionBox';
import S from './style';

interface ApplicantBaseInfoProps {
  applicantId: number;
}

export default function QuestionSection({ applicantId }: ApplicantBaseInfoProps) {
  // TODO: useApplicantDetail 훅에서 데이터를 가져와야 합니다. API명세 완료시 진행
  return (
    <S.Container>
      <S.InnerContainer>
        <QuestionBox
          header="이력서"
          type="file"
          fileName="이력서 파일 명_어쩌구.pdf"
          onFileDownload={() => console.log('File downloaded')}
        />
        <QuestionBox
          header="자기소개"
          type="text"
          // eslint-disable-next-line max-len
          content="행정권은 대통령을 수반으로 하는 정부에 속한다. 대통령은 국가의 원수이며, 외국에 대하여 국가를 대표한다. 이 헌법은 1988년 2월 25일부터 시행한다. 다만, 이 헌법을 시행하기 위하여 필요한 법률의 제정·개정과 이 헌법에 의한 대통령 및 국회의원의 선거 기타 이 헌법시행에 관한 준비는 이 헌법시행 전에 할 수 있다."
        />
        <QuestionBox
          header="질문 예시"
          type="text"
          content="뽑아주세요."
        />
        <QuestionBox
          header="질문 예시"
          type="text"
          content="뽑아주세요."
        />
        <QuestionBox
          header="질문 예시"
          type="text"
          content="뽑아주세요."
        />
      </S.InnerContainer>
    </S.Container>
  );
}
