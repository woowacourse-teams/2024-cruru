import { EvaluationResult } from '@customTypes/applicant';
import formatDate from '@utils/formatDate';

import { HiOutlineClock } from 'react-icons/hi';
import { EVALUATION_SCORE } from '../constants';
import S from './style';

interface EvaluationCardProps {
  evaluationResult: EvaluationResult;
}

export default function EvaluationCard({ evaluationResult }: EvaluationCardProps) {
  /**
   * 추후 평가자 이름 삽입 기능이 추가되면 아래 내용을 적용할 예정입니다.
   * - by 아르, 24.08.21
   */
  // const evaluatorName = evaluationResult.evaluatorName || '평가자 이름';
  const createdDate = evaluationResult.createdDate ? formatDate(evaluationResult.createdDate) : '날짜 정보 없음';

  return (
    <S.CardContainer>
      <S.ResultFlag $score={evaluationResult.score}>{EVALUATION_SCORE[evaluationResult.score]}</S.ResultFlag>
      <S.ResultComment>{evaluationResult.content}</S.ResultComment>
      <S.EvaluatorDetailContainer>
        <S.EvaluatedDate>
          <HiOutlineClock size="1.2rem" />
          {createdDate}
        </S.EvaluatedDate>
      </S.EvaluatorDetailContainer>
    </S.CardContainer>
  );
}
