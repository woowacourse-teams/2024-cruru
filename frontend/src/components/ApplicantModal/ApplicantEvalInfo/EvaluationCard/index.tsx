import { EvaluationResult } from '@customTypes/applicant';
import formatDate from '@utils/formatDate';

import { evaluationMutations } from '@hooks/evaluations';

import { HiOutlineClock } from 'react-icons/hi';
import { HiOutlineTrash } from 'react-icons/hi2';
import { FiUser } from 'react-icons/fi';

import { EVALUATION_SCORE } from '../constants';
import S from './style';

interface EvaluationCardProps {
  evaluationResult: EvaluationResult;
  processId: number;
  applicantId: number;
  evaluationId: number;
}

export default function EvaluationCard({
  evaluationResult,
  processId,
  applicantId,
  evaluationId,
}: EvaluationCardProps) {
  const createdDate = evaluationResult.createdDate ? formatDate(evaluationResult.createdDate) : '날짜 정보 없음';

  const { mutate: deleteEvaluation, isPending: isDeletePending } = evaluationMutations.useDeleteEvaluation({
    processId,
    applicantId,
  });

  const handleClickDeleteButton = () => {
    if (window.confirm('삭제하신 평가는 다시 복구할 수 없습니다.\n삭제하시겠습니까?')) {
      deleteEvaluation({ evaluationId });
    }
  };

  return (
    <S.CardContainer>
      <S.CardHeaderContainer>
        <S.ResultFlag $score={evaluationResult.score}>{EVALUATION_SCORE[evaluationResult.score]}</S.ResultFlag>
        <S.UtilButtonsContainer>
          <S.DeleteButton
            type="button"
            onClick={handleClickDeleteButton}
            disabled={isDeletePending}
          >
            <HiOutlineTrash width="1.2rem" />
            삭제
          </S.DeleteButton>
        </S.UtilButtonsContainer>
      </S.CardHeaderContainer>
      <S.ResultComment>{evaluationResult.content}</S.ResultComment>
      <S.EvaluatorDetailContainer>
        {evaluationResult.evaluator && (
          <S.EvaluatorName>
            <FiUser size="1.2rem" />
            {evaluationResult.evaluator}
          </S.EvaluatorName>
        )}
        <S.EvaluatedDate>
          <HiOutlineClock size="1.2rem" />
          {createdDate}
        </S.EvaluatedDate>
      </S.EvaluatorDetailContainer>
    </S.CardContainer>
  );
}
