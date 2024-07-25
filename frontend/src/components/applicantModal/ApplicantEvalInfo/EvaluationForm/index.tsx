import { useState } from 'react';

import RadioField from '@components/common/RadioField';
import Button from '@components/common/Button';
import TextField from '@components/common/TextField';

import { EVALUATION_SCORE } from '../constants';
import S from './style';

interface EvaluationFormProps {
  onClose: () => void;
}

export default function EvaluationForm({ onClose }: EvaluationFormProps) {
  const [formState, setFormState] = useState({
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

  const handleSubmit = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    if (Object.keys(EVALUATION_SCORE).includes(formState.score)) {
      // TODO: API 연결 및 mutation 함수 연결

      onClose();
    }
  };

  const evaluationOptions = Object.entries(EVALUATION_SCORE).map(([key, value]) => ({
    value: key,
    label: value,
  }));

  return (
    <S.EvaluationForm>
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
          onClick={onClose}
          size="md"
        >
          취소
        </Button>
        <Button
          type="button"
          color="primary"
          size="md"
          disabled={formState.score === ''}
          onClick={handleSubmit}
        >
          평가 저장
        </Button>
      </S.FormButtonWrapper>
    </S.EvaluationForm>
  );
}
