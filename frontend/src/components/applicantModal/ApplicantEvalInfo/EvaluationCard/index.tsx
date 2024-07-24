import { EVALUATION_SCORE } from '@constants/constants';
import { EvaluationScore } from '@customTypes/applicant';
import formatDate from '@utils/formatDate';
import S from './style';

interface EvaluationCardProps {
  evaluatorName: string;
  evaluatedDate: string;
  score: EvaluationScore;
  comment: string;
}

export default function EvaluationCard({ evaluatorName, evaluatedDate, score, comment }: EvaluationCardProps) {
  return (
    <S.CardContainer>
      <S.EvaluatorDetailContainer>
        <S.EvaluatorImagePlaceholder />
        <S.EvaluatorDetail>
          <S.EvaluatorName>{evaluatorName}</S.EvaluatorName>
          <S.EvaluatedDate>{formatDate(evaluatedDate)}</S.EvaluatedDate>
        </S.EvaluatorDetail>
      </S.EvaluatorDetailContainer>
      <S.ResultFlag
        $color={EVALUATION_SCORE[score].color}
        $bgColor={EVALUATION_SCORE[score].bgColor}
      >
        {EVALUATION_SCORE[score].description}
      </S.ResultFlag>
      <S.ResultComment>{comment}</S.ResultComment>
    </S.CardContainer>
  );
}
