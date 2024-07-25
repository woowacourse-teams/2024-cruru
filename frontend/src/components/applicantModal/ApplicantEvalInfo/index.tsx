import { useState } from 'react';

import { EvaluationResult } from '@customTypes/applicant';

import EvaluationHeader from '@components/applicantModal/ApplicantEvalInfo/EvaluationHeader';
import EvaluationForm from '@components/applicantModal/ApplicantEvalInfo/EvaluationForm';
import EvaluationAddButton from '@components/applicantModal/ApplicantEvalInfo/EvaluationAddButton';

import S from './style';
import EvaluationCard from './EvaluationCard';

interface ApplicantEvalInfoProps {
  applicantId: number;
  evaluationResults: EvaluationResult[];
}

export default function ApplicantEvalInfo({ applicantId, evaluationResults }: ApplicantEvalInfoProps) {
  const [isFormOpened, setIsFormOpened] = useState<boolean>(false);

  const FormSection = isFormOpened ? (
    <EvaluationForm
      applicantId={applicantId}
      onClose={() => setIsFormOpened(false)}
    />
  ) : (
    <EvaluationAddButton onClick={() => setIsFormOpened(true)} />
  );

  return (
    <S.Wrapper>
      <EvaluationHeader
        title="지원자 평가"
        description="지원자에 대한 평가 내용입니다."
      />

      <S.FormContainer>{FormSection}</S.FormContainer>

      <S.EvaluationListContainer>
        {evaluationResults.map((evaluationResult) => (
          <EvaluationCard
            key={evaluationResult.evaluationId}
            evaluationResult={evaluationResult}
          />
        ))}
      </S.EvaluationListContainer>
    </S.Wrapper>
  );
}
