import { Question } from '@customTypes/apply';
import useLocalStorageState from '@hooks/useLocalStorageState';

interface AnswerFormData {
  [key: string]: string[];
}

export const useAnswers = (questions: Question[], LOCALSTORAGE_KEY: string, enableStorage: boolean) => {
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
  };
};
