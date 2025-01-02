import { useAnswers } from '@components/recruitmentPost/ApplyForm/useAnswers';
import { Question } from '@customTypes/apply';
import { createContext, useContext, useMemo, PropsWithChildren } from 'react';

interface ApplyAnswerContextType {
  initialValues: {
    name: string;
    email: string;
    phone: string;
  };
  answers: {
    [key: string]: string[];
  };
  changeHandler: {
    SHORT_ANSWER: (id: string, value: string) => void;
    LONG_ANSWER: (id: string, value: string) => void;
    MULTIPLE_CHOICE: (id: string, value: string) => void;
    SINGLE_CHOICE: (id: string, value: string) => void;
  };
  isRequiredFieldsIncomplete: () => boolean;
}

const ApplyAnswerContext = createContext<ApplyAnswerContextType | null>(null);

interface ApplyAnswerContextProps extends PropsWithChildren {
  questions: Question[];
  applyFormId: string;
}

export function ApplyAnswerProvider({ questions, applyFormId, children }: ApplyAnswerContextProps) {
  const initialValues = useMemo(() => ({ name: '', email: '', phone: '' }), []);
  const { answers, changeHandler, isRequiredFieldsIncomplete } = useAnswers(questions, applyFormId);

  const valueObj = useMemo(
    () => ({
      initialValues,
      answers,
      changeHandler,
      isRequiredFieldsIncomplete,
    }),
    [initialValues, answers, changeHandler, isRequiredFieldsIncomplete],
  );

  return <ApplyAnswerContext.Provider value={valueObj}>{children}</ApplyAnswerContext.Provider>;
}

export const useApplyAnswer = () => {
  const context = useContext(ApplyAnswerContext);
  if (!context) {
    throw new Error('useApplyAnswer은 ApplyAnswerProvider내부에서 관리되어야 합니다.');
  }
  return context;
};
