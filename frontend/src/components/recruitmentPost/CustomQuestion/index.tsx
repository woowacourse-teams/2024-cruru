import TextField from '@components/common/TextField';
import InputField from '@components/common/InputField';
import RadioLabelField from '@components/common/RadioLabelField';
import CheckboxLabelField from '@components/common/CheckboxLabelField';
import { Question } from '@customTypes/apply';
import { ChangeEventHandler } from 'react';

interface CustomQuestionProps {
  question: Question;
  value: string[];
  onChange?: (id: string, value: string) => void;
}

export default function CustomQuestion({ question, value = [], onChange = () => {} }: CustomQuestionProps) {
  const { type, questionId, label } = question;

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
        maxLength={50}
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
        maxLength={5000}
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
      />
    );
  }

  return null;
}
