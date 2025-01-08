import { useState } from 'react';

import useEvaluationQuery from '@hooks/useEvaluationQuery';
import { useDeleteEvaluationMutation } from '@hooks/useEvaluationMutation';

import EvaluationForm from './EvaluationForm';
import EvaluationAddButton from './EvaluationAddButton';
import EvaluationCard from './EvaluationCard';

import S from './style';

interface ApplicantEvalInfoProps {
  applicantId: number;
  processId: number;
  isCurrentProcess: boolean;
}

export default function ApplicantEvalInfo({ applicantId, processId, isCurrentProcess }: ApplicantEvalInfoProps) {
  const { evaluationList } = useEvaluationQuery({ applicantId, processId });
  const [isFormOpened, setIsFormOpened] = useState<boolean>(false);

  const { mutate: deleteEvaluation, isPending: isDeletePending } = useDeleteEvaluationMutation({
    processId,
    applicantId,
  });

  const renderFormSection = () => {
    if (!isCurrentProcess) return null;

    if (isFormOpened) {
      return (
        <EvaluationForm
          processId={processId}
          applicantId={applicantId}
          onClose={() => setIsFormOpened(false)}
        />
      );
    }
    return <EvaluationAddButton onClick={() => setIsFormOpened(true)} />;
  };

  const handleDeleteEvaluation = (evaluationId: number) => {
    deleteEvaluation({ evaluationId });
  };

  return (
    <S.Wrapper>
      <S.EvaluationListContainer>
        {evaluationList.map((evaluationResult) => (
          <EvaluationCard
            key={evaluationResult.evaluationId}
            evaluationResult={evaluationResult}
            onDelete={() => handleDeleteEvaluation(evaluationResult.evaluationId)}
            isDeletePending={isDeletePending}
          />
        ))}
      </S.EvaluationListContainer>

      <S.FormContainer>{renderFormSection()}</S.FormContainer>
    </S.Wrapper>
  );
}
