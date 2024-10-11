import { useState } from 'react';
import useEvaluationQuery from '@hooks/useEvaluationQuery';
import EvaluationForm from './EvaluationForm';
import EvaluationAddButton from './EvaluationAddButton';
import S from './style';
import EvaluationCard from './EvaluationCard';

interface ApplicantEvalInfoProps {
  applicantId: number;
  processId: number;
  isCurrentProcess: boolean;
}

export default function ApplicantEvalInfo({ applicantId, processId, isCurrentProcess }: ApplicantEvalInfoProps) {
  const { evaluationList } = useEvaluationQuery({ applicantId, processId });
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
            evaluationResult={evaluationResult}
          />
        ))}
      </S.EvaluationListContainer>

      <S.FormContainer>{renderFormSection()}</S.FormContainer>
    </S.Wrapper>
  );
}
