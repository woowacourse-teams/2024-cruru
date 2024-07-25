import { useState } from 'react';
import useEvaluationQuery from '@hooks/useEvaluationQuery';
import EvaluationForm from './EvaluationForm';
import EvaluationAddButton from './EvaluationAddButton';
import S from './style';
import EvaluationCard from './EvaluationCard';

interface ApplicantEvalInfoProps {
  applicantId: number;
}

export default function ApplicantEvalInfo({ applicantId }: ApplicantEvalInfoProps) {
  const processId = 1;
  const { evaluationList } = useEvaluationQuery({ applicantId, processId });
  const [isFormOpened, setIsFormOpened] = useState<boolean>(false);

  const FormSection = isFormOpened ? (
    <EvaluationForm
      processId={processId}
      applicantId={applicantId}
      onClose={() => setIsFormOpened(false)}
    />
  ) : (
    <EvaluationAddButton onClick={() => setIsFormOpened(true)} />
  );

  return (
    <S.Wrapper>
      <S.FormContainer>{FormSection}</S.FormContainer>

      <S.EvaluationListContainer>
        {evaluationList.map((evaluationResult) => (
          <EvaluationCard
            key={evaluationResult.evaluationId}
            evaluationResult={evaluationResult}
          />
        ))}
      </S.EvaluationListContainer>
    </S.Wrapper>
  );
}
