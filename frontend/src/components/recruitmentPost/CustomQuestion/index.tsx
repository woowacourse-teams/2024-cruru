import TextField from '@components/_common/molecules/TextField';
import InputField from '@components/_common/molecules/InputField';
import RadioLabelField from '@components/_common/atoms/RadioLabelField';
import CheckboxLabelField from '@components/_common/CheckboxLabelField';
import { Question } from '@customTypes/apply';
import { ChangeEventHandler } from 'react';
import { QUESTION_INPUT_LENGTH } from '@constants/constants';

interface CustomQuestionProps {
  question: Question;
  value: string[];
  isLengthVisible?: boolean;
  onChange?: (id: string, value: string) => void;
}

export default function CustomQuestion({
  question,
  value = [],
  isLengthVisible = false,
  onChange = () => {},
}: CustomQuestionProps) {
  const { type, questionId, label, required } = question;

  if (type === 'SHORT_ANSWER') {
    const handleChange: ChangeEventHandler<HTMLInputElement> = (e) => {
      onChange(questionId, e.target.value);
    };

    return (
      <InputField
        value={value[0] ?? ''}
        onChange={handleChange}
        label={label}
        name={questionId}
        maxLength={QUESTION_INPUT_LENGTH.SHORT_ANSWER}
        isLengthVisible={isLengthVisible}
        required={required}
      />
    );
  }

  if (type === 'LONG_ANSWER') {
    const handleChange: ChangeEventHandler<HTMLTextAreaElement> = (e) => {
      onChange(questionId, e.target.value);
    };

    return (
      <TextField
        value={value[0] ?? ''}
        label={label}
        name={questionId}
        onChange={handleChange}
        resize={false}
        style={{ height: 'calc(2.4rem * 10 + 1.2rem)' }}
        maxLength={QUESTION_INPUT_LENGTH.LONG_ANSWER}
        isLengthVisible={isLengthVisible}
        required={required}
      />
    );
  }

  if (type === 'MULTIPLE_CHOICE') {
    return (
      <CheckboxLabelField
        label={question.label}
        options={question.choices.map((choice) => ({
          optionLabel: choice.label,
          isChecked: value.includes(choice.label),
          name: question.questionId,
          onToggle: () => onChange(question.questionId, choice.label),
        }))}
        required={required}
      />
    );
  }

  if (type === 'SINGLE_CHOICE') {
    return (
      <RadioLabelField
        label={question.label}
        options={question.choices.map((choice) => ({
          optionLabel: choice.label,
          isChecked: value.includes(choice.label),
          onToggle: () => onChange(question.questionId, choice.label),
        }))}
        required={required}
      />
    );
  }

  return null;
}
