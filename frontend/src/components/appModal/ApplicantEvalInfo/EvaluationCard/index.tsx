import { EvaluationResult } from '@customTypes/applicant';
import formatDate from '@utils/formatDate';

import S from './style';
import { EVALUATION_SCORE } from '../constants';

interface EvaluationCardProps {
  evaluationResult: EvaluationResult;
}

export default function EvaluationCard({ evaluationResult }: EvaluationCardProps) {
  /**
   * 평가자 이름 삽입 기능 추가시 수정 예정입니다. (24/8/16)
   */
  const evaluatorName = evaluationResult.evaluatorName || '평가자 이름';
  const createdDate = evaluationResult.createdDate ? formatDate(evaluationResult.createdDate) : '날짜 정보 없음';

  return (
    <S.CardContainer>
      <S.EvaluatorDetailContainer>
        <S.EvaluatorImagePlaceholder />
        <S.EvaluatorDetail>
          <S.EvaluatorName>{evaluatorName}</S.EvaluatorName>
          <S.EvaluatedDate>{createdDate}</S.EvaluatedDate>
        </S.EvaluatorDetail>
      </S.EvaluatorDetailContainer>
      <S.ResultFlag $score={evaluationResult.score}>{EVALUATION_SCORE[evaluationResult.score]}</S.ResultFlag>
      <S.ResultComment>{evaluationResult.content}</S.ResultComment>
    </S.CardContainer>
  );
}
