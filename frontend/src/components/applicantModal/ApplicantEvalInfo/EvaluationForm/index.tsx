import { useState } from 'react';

import RadioField from '@components/common/RadioField';
import Button from '@components/common/Button';
import TextField from '@components/common/TextField';

import { EvaluationResult } from '@customTypes/applicant';
import { EVALUATION_SCORE } from '../constants';
import S from './style';

interface EvaluationFormProps {
  onCancelSubmit: () => void;
}

export default function EvaluationForm({ onCancelSubmit }: EvaluationFormProps) {
  const [formState, setFormState] = useState<EvaluationResult>({
    score: '',
    content: '',
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
    const { name, value } = event.target;
    setFormState((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    // TODO: API 연결 및 mutation 함수 연결
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
        onChange={(value: string) => handleChangeScore(value)}
      />

      <TextField
        placeholder="지원자에 대한 평가 내용을 입력해주세요."
        value={formState.content}
        onChange={handleChangeContent}
        name="content"
      />

      <S.FormButtonWrapper>
        <Button
          type="reset"
          color="white"
          onClick={onCancelSubmit}
          size="md"
        >
          취소
        </Button>
        <Button
          type="submit"
          color="primary"
          size="md"
        >
          평가 저장
        </Button>
      </S.FormButtonWrapper>
    </S.EvaluationForm>
  );
}
