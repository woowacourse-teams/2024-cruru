import { useAnswers } from '@components/recruitmentPost/ApplyForm/useAnswers';
import { RecruitmentPostTabItems } from '@components/recruitmentPost/RecruitmentPostTab';
import { Question } from '@customTypes/apply';
import useLocalStorageState from '@hooks/useLocalStorageState';
import { createContext, useContext, useMemo, PropsWithChildren, useState } from 'react';

interface InitialValues {
  name: string;
  email: string;
  phone: string;
}

interface ApplyAnswerContextType {
  initialValues: {
    name: string;
    email: string;
    phone: string;
  };
  baseInfoHandlers: {
    handleName: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleEmail: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handlePhone: (e: React.ChangeEvent<HTMLInputElement>) => void;
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
  resetAnswerStorage: () => void;
}

const ApplyAnswerContext = createContext<ApplyAnswerContextType | null>(null);

interface ApplyAnswerContextProps extends PropsWithChildren {
  questions: Question[];
  applyFormId: string;
  moveTabByParam: (value: RecruitmentPostTabItems) => void;
}

export function ApplyAnswerProvider({ questions, applyFormId, moveTabByParam, children }: ApplyAnswerContextProps) {
  const LOCALSTORAGE_KEY = `${applyFormId}-initial-values`;

  const [enableStorage] = useState(() => {
    if (window.localStorage.getItem(LOCALSTORAGE_KEY)) {
      if (window.confirm('이전 작성중인 지원서가 있습니다. 이어서 진행하시겠습니까?')) {
        moveTabByParam('지원하기');
        return true;
      }
    }
    return false;
  });

  const [initialValues, setInitialValues] = useLocalStorageState<InitialValues>(
    { name: '', email: '', phone: '' },
    {
      key: LOCALSTORAGE_KEY,
      enableStorage,
    },
  );

  const baseInfoHandlers = useMemo(
    () => ({
      handleName: (e: React.ChangeEvent<HTMLInputElement>) => {
        setInitialValues((prev) => ({
          ...prev,
          name: e.target.value,
        }));
      },
      handleEmail: (e: React.ChangeEvent<HTMLInputElement>) => {
        setInitialValues((prev) => ({
          ...prev,
          email: e.target.value,
        }));
      },
      handlePhone: (e: React.ChangeEvent<HTMLInputElement>) => {
        setInitialValues((prev) => ({
          ...prev,
          phone: e.target.value,
        }));
      },
    }),
    [setInitialValues],
  );

  const { answers, changeHandler, isRequiredFieldsIncomplete, resetAnswerStorage } = useAnswers(questions, applyFormId);

  const valueObj = useMemo(
    () => ({
      initialValues,
      baseInfoHandlers,
      answers,
      changeHandler,
      isRequiredFieldsIncomplete,
      resetAnswerStorage,
    }),
    [initialValues, baseInfoHandlers, answers, changeHandler, isRequiredFieldsIncomplete, resetAnswerStorage],
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
