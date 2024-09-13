import { useState } from 'react';

import RadioField from '@components/_common/molecules/RadioField';
import Button from '@components/_common/atoms/Button';
import TextField from '@components/_common/molecules/TextField';

import useEvaluationMutation from '@hooks/useEvaluationMutation';
import ValidationError from '@utils/errors/ValidationError';
import { validateEvalContent } from '@domain/validations/evaluation';

import Spinner from '@components/_common/atoms/Spinner';
import { EVALUATION_CONTENT_MAX_LENGTH, EVALUATION_SCORE } from '../constants';
import S from './style';

interface EvaluationData {
  score: string;
  content: string;
}

interface EvaluationFormProps {
  processId: number;
  applicantId: number;
  onClose: () => void;
}

export default function EvaluationForm({ processId, applicantId, onClose }: EvaluationFormProps) {
  const [formState, setFormState] = useState<EvaluationData>({ score: '', content: '' });
  const [contentErrorMessage, setContentErrorMessage] = useState<string | undefined>();
  const { mutate: submitNewEvaluation, isPending } = useEvaluationMutation({
    processId,
    applicantId,
    closeOnSuccess: onClose,
  });

  const handleChangeScore = (value: string) => {
    if (Object.keys(EVALUATION_SCORE).includes(value)) {
      setFormState((prevState) => ({
        ...prevState,
        score: value,
      }));
    }
  };

  const handleChangeContent = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    try {
      const { name, value } = event.target;
      validateEvalContent(value);
      setFormState((prevState) => ({
        ...prevState,
        [name]: value,
      }));
      setContentErrorMessage(undefined);
    } catch (error) {
      if (error instanceof ValidationError) {
        setContentErrorMessage(error.message);
      }
    }
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (Object.keys(EVALUATION_SCORE).includes(formState.score)) {
      submitNewEvaluation({ processId, applicantId, ...formState });
    }
  };

  const evaluationOptions = Object.entries(EVALUATION_SCORE).map(([key, value]) => ({
    value: key,
    label: value,
  }));

  return (
    <S.EvaluationForm onSubmit={handleSubmit}>
      <RadioField
        options={evaluationOptions}
        selectedValue={formState.score}
        optionsGap="0.8rem"
        labelSize="1.3rem"
        onChange={(value: string) => handleChangeScore(value)}
      />

      <TextField
        name="content"
        placeholder="지원자에 대한 평가 내용을 입력해주세요."
        value={formState.content}
        maxLength={EVALUATION_CONTENT_MAX_LENGTH}
        onChange={handleChangeContent}
        error={contentErrorMessage}
      />

      <S.FormButtonWrapper>
        <Button
          type="reset"
          color="white"
          onClick={onClose}
          size="sm"
          disabled={isPending}
        >
          취소
        </Button>
        <Button
          type="submit"
          color="primary"
          size="sm"
          disabled={formState.score === '' || !!contentErrorMessage}
        >
          {isPending ? (
            <S.SpinnerContainer>
              <Spinner width={25} />
            </S.SpinnerContainer>
          ) : (
            '평가 저장'
          )}
        </Button>
      </S.FormButtonWrapper>
    </S.EvaluationForm>
  );
}
