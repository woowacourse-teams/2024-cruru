import { EvaluationResult } from '@customTypes/applicant';
import formatDate from '@utils/formatDate';

import S from './style';
import { EVALUATION_SCORE } from '../constants';

interface EvaluationCardProps {
  evaluatorName: string;
  evaluatedDate: string;
  result: EvaluationResult;
}

export default function EvaluationCard({ evaluatorName, evaluatedDate, result }: EvaluationCardProps) {
  return (
    <S.CardContainer>
      <S.EvaluatorDetailContainer>
        <S.EvaluatorImagePlaceholder />
        <S.EvaluatorDetail>
          <S.EvaluatorName>{evaluatorName}</S.EvaluatorName>
          <S.EvaluatedDate>{formatDate(evaluatedDate)}</S.EvaluatedDate>
        </S.EvaluatorDetail>
      </S.EvaluatorDetailContainer>
      <S.ResultFlag $score={result.score}>{EVALUATION_SCORE[result.score]}</S.ResultFlag>
      <S.ResultComment>{result.content}</S.ResultComment>
    </S.CardContainer>
  );
}
