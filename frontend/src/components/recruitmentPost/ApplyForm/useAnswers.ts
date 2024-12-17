import { Question } from '@customTypes/apply';
import { useState } from 'react';

interface AnswerFormData {
  [key: string]: string[];
}

export const useAnswers = (questions: Question[]) => {
  // const [answers, setAnswers] = useState<AnswerFormData>({});
  // TODO: useLocalStorageState() 사용하기
  const [answers, setAnswers] = useState<AnswerFormData>(
    questions.reduce((acc, question) => ({ ...acc, [question.questionId]: [] }), {} as AnswerFormData),
  );

  // useEffect(() => {
  //   if (questions.length > 0) {
  //     const initialAnswers = questions.reduce(
  //       (acc, question) => ({ ...acc, [question.questionId]: [] }),
  //       {} as AnswerFormData,
  //     );
  //     setAnswers(initialAnswers);
  //   }
  // }, [questions]);

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
