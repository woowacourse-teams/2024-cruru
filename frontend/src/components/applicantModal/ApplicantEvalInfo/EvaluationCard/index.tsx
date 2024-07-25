import { EvaluationResult } from '@customTypes/applicant';
import formatDate from '@utils/formatDate';

import S from './style';
import { EVALUATION_SCORE } from '../constants';

interface EvaluationCardProps {
  evaluationResult: EvaluationResult;
}

export default function EvaluationCard({ evaluationResult }: EvaluationCardProps) {
  /**
   * 평가 조회 API에 누락되어 있는 데이터들입니다.
   * 추후 데이터 항목 추가시 수정 예정입니다. (24/7/25)
   */
  const evaluatorName = evaluationResult.evaluatorName || '평가자 이름';
  const createdAt = evaluationResult.createdAt ? formatDate(evaluationResult.createdAt) : '24. 07. 25';

  return (
    <S.CardContainer>
      <S.EvaluatorDetailContainer>
        <S.EvaluatorImagePlaceholder />
        <S.EvaluatorDetail>
          <S.EvaluatorName>{evaluatorName}</S.EvaluatorName>
          <S.EvaluatedDate>{createdAt}</S.EvaluatedDate>
        </S.EvaluatorDetail>
      </S.EvaluatorDetailContainer>
      <S.ResultFlag $score={evaluationResult.score}>{EVALUATION_SCORE[evaluationResult.score]}</S.ResultFlag>
      <S.ResultComment>{evaluationResult.content}</S.ResultComment>
    </S.CardContainer>
  );
}
