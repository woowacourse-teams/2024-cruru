import QuestionBox from '@components/common/QuestionBox';
import specificApplicant from '@hooks/useSpecificApplicant';
import S from './style';

interface ApplicantBaseInfoProps {
  applicantId: number;
}

export default function QuestionSection({ applicantId }: ApplicantBaseInfoProps) {
  const { data } = specificApplicant.useGetDetailInfo({ applicantId });

  return (
    <S.Container>
      <S.InnerContainer>
        {data?.details.map((detail) => (
          <QuestionBox
            key={detail.order_index}
            header={detail.question}
            type="text"
            content={detail.answer}
          />
        ))}
      </S.InnerContainer>
    </S.Container>
  );
}
