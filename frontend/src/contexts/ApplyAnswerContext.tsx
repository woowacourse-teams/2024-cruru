import { useAnswers } from '@components/recruitmentPost/ApplyForm/useAnswers';
import { RecruitmentPostTabItems } from '@components/recruitmentPost/RecruitmentPostTab';
import { Question } from '@customTypes/apply';
import useLocalStorageState from '@hooks/useLocalStorageState';
import { createExecutionTracker } from '@utils/createExecutionTracker';
import { createContext, useContext, useMemo, PropsWithChildren, useCallback, useState } from 'react';

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
    handleName: (value: string) => void;
    handleEmail: (value: string) => void;
    handlePhone: (value: string) => void;
  };
  resetStorage: () => void;
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
  moveTabByParam: (value: RecruitmentPostTabItems) => void;
}

const ExecutionTracker = createExecutionTracker();

export function ApplyAnswerProvider({ questions, applyFormId, moveTabByParam, children }: ApplyAnswerContextProps) {
  const LOCALSTORAGE_KEY = `${applyFormId}-apply-form`;

  const [enableStorage] = useState(() => {
    if (!ExecutionTracker.executeIfFirst()) return true;

    const prevSavedAnswer = window.localStorage.getItem(LOCALSTORAGE_KEY);
    if (prevSavedAnswer) {
      if (!isValidKey(prevSavedAnswer, questions)) {
        return false;
      }

      if (!isValidValue(prevSavedAnswer)) {
        return false;
      }

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
      handleName: (value: string) => {
        setInitialValues((prev) => ({
          ...prev,
          name: value,
        }));
      },
      handleEmail: (value: string) => {
        setInitialValues((prev) => ({
          ...prev,
          email: value,
        }));
      },
      handlePhone: (value: string) => {
        setInitialValues((prev) => ({
          ...prev,
          phone: value,
        }));
      },
    }),
    [setInitialValues],
  );

  const { answers, changeHandler, isRequiredFieldsIncomplete, resetAnswerStorage } = useAnswers(
    questions,
    LOCALSTORAGE_KEY,
    enableStorage,
  );

  const resetStorage = useCallback(() => {
    window.localStorage.removeItem(LOCALSTORAGE_KEY);
    resetAnswerStorage();
  }, [LOCALSTORAGE_KEY, resetAnswerStorage]);

  const valueObj = useMemo(
    () => ({
      initialValues,
      baseInfoHandlers,
      resetStorage,
      answers,
      changeHandler,
      isRequiredFieldsIncomplete,
    }),
    [initialValues, baseInfoHandlers, resetStorage, answers, changeHandler, isRequiredFieldsIncomplete],
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

function isValidKey(prevSavedAnswer: string, questions: Question[]) {
  const prevSavedAnswerKeys = Object.keys(JSON.parse(prevSavedAnswer));
  return prevSavedAnswerKeys.every(
    (key) => questions.some(({ questionId }) => questionId === key) || ['name', 'email', 'phone'].includes(key),
  );
}

function isValidValue(prevSavedAnswer: string) {
  const prevSavedAnswerValues = Object.values(JSON.parse(prevSavedAnswer));
  return prevSavedAnswerValues.some((value) => value !== '');
}
