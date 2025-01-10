import { useState } from 'react';

import Button from '@components/_common/atoms/Button';
import InputField from '@components/_common/molecules/InputField';
import TextField from '@components/_common/molecules/TextField';

import { validateEvalContent, validateEvaluator } from '@domain/validations/evaluation';
import useEvaluationMutation from '@hooks/useEvaluationMutation';
import ValidationError from '@utils/errors/ValidationError';

import Spinner from '@components/_common/atoms/Spinner';
import StarRating from '@components/_common/molecules/StarRating';
import { EVALUATION_CONTENT_MAX_LENGTH, EVALUATION_EVALUATOR_MAX_LENGTH } from '../constants';
import S from './style';

interface EvaluationData {
  evaluator: string;
  score: number;
  content: string;
}

interface EvaluationFormProps {
  processId: number;
  applicantId: number;
  onClose: () => void;
}

interface ValidateEvaluatorParams {
  name: string;
  value: string;
  trim?: 'none' | 'start' | 'both';
}

export default function EvaluationForm({ processId, applicantId, onClose }: EvaluationFormProps) {
  const [formState, setFormState] = useState<EvaluationData>({ evaluator: '', score: 0, content: '' });
  const [contentErrorMessage, setContentErrorMessage] = useState<string | undefined>();
  const [evaluatorErrorMessage, setEvaluatorErrorMessage] = useState<string | undefined>();
  const { mutate: submitNewEvaluation, isPending } = useEvaluationMutation({
    processId,
    applicantId,
    closeOnSuccess: onClose,
  });

  const handleChangeScore = (value: number) => {
    setFormState((prevState) => ({
      ...prevState,
      score: value,
    }));
  };

  const validateAndUpdateEvaluator = ({ name, value, trim = 'none' }: ValidateEvaluatorParams) => {
    const trimmedValue = trim === 'both' ? value.trim() : trim === 'start' ? value.trimStart() : value;

    setFormState((prevState) => ({
      ...prevState,
      [name]: trimmedValue,
    }));

    try {
      validateEvaluator(trimmedValue);
      setEvaluatorErrorMessage(undefined);
    } catch (error) {
      if (error instanceof ValidationError) {
        setEvaluatorErrorMessage(error.message);
      }
    }
  };

  const handleChangeEvaluator = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    validateAndUpdateEvaluator({ name, value, trim: 'start' });
  };

  const handleBlurEvaluator = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    validateAndUpdateEvaluator({ name, value, trim: 'both' });
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

    if (window.confirm('평가를 등록한 후에는 수정하거나 삭제할 수 없습니다.\n등록하시겠습니까?')) {
      submitNewEvaluation({ processId, applicantId, ...formState });
    }
  };

  return (
    <S.EvaluationForm onSubmit={handleSubmit}>
      <S.Header>
        <StarRating
          rating={formState.score}
          handleRating={handleChangeScore}
        />
        <S.CancelButton
          type="reset"
          onClick={onClose}
          disabled={isPending}
        >
          입력 취소
        </S.CancelButton>
      </S.Header>

      <InputField
        name="evaluator"
        placeholder="평가자 이름을 입력해주세요."
        value={formState.evaluator}
        maxLength={EVALUATION_EVALUATOR_MAX_LENGTH}
        onChange={handleChangeEvaluator}
        onBlur={handleBlurEvaluator}
        error={evaluatorErrorMessage}
      />

      <TextField
        name="content"
        placeholder="지원자에 대한 평가 내용을 입력해주세요."
        value={formState.content}
        maxLength={EVALUATION_CONTENT_MAX_LENGTH}
        onChange={handleChangeContent}
        error={contentErrorMessage}
        resize={false}
        rows={5}
      />

      <S.FormButtonWrapper>
        <Button
          type="submit"
          color="primary"
          size="fillContainer"
          disabled={formState.score === 0 || !!contentErrorMessage || !!evaluatorErrorMessage}
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
