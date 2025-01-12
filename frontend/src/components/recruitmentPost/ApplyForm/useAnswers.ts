import { Question } from '@customTypes/apply';
import useLocalStorageState from '@hooks/useLocalStorageState';
import { useState } from 'react';

interface AnswerFormData {
  [key: string]: string[];
}

export const useAnswers = (questions: Question[], applyFormId: string) => {
  const LOCALSTORAGE_KEY = `${applyFormId}-apply-form`;

  const [enableStorage] = useState(() => {
    const prevSavedAnswer = window.localStorage.getItem(LOCALSTORAGE_KEY);
    if (!prevSavedAnswer) return false;

    const prevSavedAnswerIds = Object.keys(JSON.parse(prevSavedAnswer));
    if (prevSavedAnswerIds.every((id) => questions.some(({ questionId }) => questionId === id))) {
      return window.confirm('이전 작성중인 지원서가 있습니다. 이어서 진행하시겠습니까?');
    }
    return false;
  });

  const resetAnswerStorage = () => {
    window.localStorage.removeItem(LOCALSTORAGE_KEY);
  };

  const [answers, setAnswers] = useLocalStorageState<AnswerFormData>(
    (() => questions.reduce((acc, question) => ({ ...acc, [question.questionId]: [] }), {} as AnswerFormData))(),
    {
      key: LOCALSTORAGE_KEY,
      enableStorage,
    },
  );

  const handleText = (id: string, value: string) => {
    setAnswers((prev) => ({ ...prev, [id]: [value] }));
  };

  const handleCheckBox = (id: string, value: string) => {
    setAnswers((prev) => {
      const prevAnswer = prev[id] || [];
      return {
        ...prev,
        [id]: prevAnswer.includes(value) ? prevAnswer.filter((answer) => answer !== value) : [...prevAnswer, value],
      };
    });
  };

  const handleRadio = (id: string, value: string) => {
    setAnswers((prev) => ({ ...prev, [id]: [value] }));
  };

  const isRequiredFieldsIncomplete = () =>
    questions.some((question) => question.required && answers[question.questionId]?.length === 0);

  return {
    answers,
    changeHandler: {
      SHORT_ANSWER: handleText,
      LONG_ANSWER: handleText,
      MULTIPLE_CHOICE: handleCheckBox,
      SINGLE_CHOICE: handleRadio,
    },
    isRequiredFieldsIncomplete,
    resetAnswerStorage,
  };
};
