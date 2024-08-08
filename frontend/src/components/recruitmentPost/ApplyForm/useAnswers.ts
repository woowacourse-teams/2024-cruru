import { Question } from '@customTypes/apply';
import { useEffect, useState } from 'react';

interface AnswerFormData {
  [key: string]: string[];
}

export const useAnswers = (questions: Question[]) => {
  const [answers, setAnswers] = useState<AnswerFormData>({});

  useEffect(() => {
    if (questions.length > 0) {
      const initialAnswers = questions.reduce(
        (acc, question) => ({ ...acc, [question.questionId]: [] }),
        {} as AnswerFormData,
      );
      setAnswers(initialAnswers);
    }
  }, [questions]);

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

  return {
    answers,
    changeHandler: {
      SHORT_ANSWER: handleText,
      LONG_ANSWER: handleText,
      MULTIPLE_CHOICE: handleCheckBox,
      SINGLE_CHOICE: handleRadio,
    },
  };
};
