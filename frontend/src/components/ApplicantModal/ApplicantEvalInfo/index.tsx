import { useState } from 'react';

import { evaluationQueries } from '@hooks/evaluations';

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
  const { evaluationList } = evaluationQueries.useGetEvaluations({ processId, applicantId });
  const [isFormOpened, setIsFormOpened] = useState<boolean>(false);

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

  return (
    <S.Wrapper>
      <S.EvaluationListContainer>
        {evaluationList.map((evaluationResult) => (
          <EvaluationCard
            key={evaluationResult.evaluationId}
            evaluationId={evaluationResult.evaluationId}
            processId={processId}
            applicantId={applicantId}
            evaluationResult={evaluationResult}
          />
        ))}
      </S.EvaluationListContainer>

      <S.FormContainer>{renderFormSection()}</S.FormContainer>
    </S.Wrapper>
  );
}
