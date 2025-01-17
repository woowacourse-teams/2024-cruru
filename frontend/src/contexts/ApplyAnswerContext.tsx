import { useAnswers } from '@components/recruitmentPost/ApplyForm/useAnswers';
import { Question } from '@customTypes/apply';
import useLocalStorageState from '@hooks/useLocalStorageState';
import { createExecutionTracker } from '@utils/createExecutionTracker';
import { createContext, useContext, useMemo, PropsWithChildren, useCallback, useState, useEffect } from 'react';

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
}

const ExecutionTracker = createExecutionTracker();

export function ApplyAnswerProvider({ questions, applyFormId, children }: ApplyAnswerContextProps) {
  const LOCALSTORAGE_BASE_INFO_KEY = `${applyFormId}-base-info`;
  const LOCALSTORAGE_ANSWER_KEY = `${applyFormId}-apply-form`;

  const [enableStorage] = useState(() => {
    if (ExecutionTracker.getHasExecuted()) return true;

    const prevBaseInfo = window.localStorage.getItem(LOCALSTORAGE_BASE_INFO_KEY);
    const prevAnswer = window.localStorage.getItem(LOCALSTORAGE_ANSWER_KEY);
    if (prevBaseInfo || prevAnswer) {
      if (prevBaseInfo && !isValidBaseInfo(prevBaseInfo)) {
        return false;
      }

      if (prevAnswer && !isValidAnswers(prevAnswer, questions)) {
        return false;
      }

      if (window.confirm('이전 작성중인 지원서가 있습니다. 이어서 진행하시겠습니까?')) {
        return true;
      }
    }
    return false;
  });

  useEffect(() => {
    if (!ExecutionTracker.getHasExecuted()) {
      ExecutionTracker.executet();
    }
  }, []);

  const [initialValues, setInitialValues] = useLocalStorageState<InitialValues>(
    { name: '', email: '', phone: '' },
    {
      key: LOCALSTORAGE_BASE_INFO_KEY,
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

  const { answers, changeHandler, isRequiredFieldsIncomplete } = useAnswers(
    questions,
    LOCALSTORAGE_ANSWER_KEY,
    enableStorage,
  );

  const resetStorage = useCallback(() => {
    window.localStorage.removeItem(LOCALSTORAGE_BASE_INFO_KEY);
    window.localStorage.removeItem(LOCALSTORAGE_ANSWER_KEY);
  }, [LOCALSTORAGE_BASE_INFO_KEY, LOCALSTORAGE_ANSWER_KEY]);

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

function isValidBaseInfo(prevSavedAnswer: string) {
  const prevSavedAnswerKeys = Object.keys(JSON.parse(prevSavedAnswer));
  const prevSavedAnswerValues = Object.values(JSON.parse(prevSavedAnswer));

  return (
    prevSavedAnswerKeys.every((key) => ['name', 'email', 'phone'].includes(key)) &&
    prevSavedAnswerValues.some((value) => value !== '')
  );
}

function isValidAnswers(prevSavedAnswer: string, questions: Question[]) {
  const prevSavedAnswerValues = Object.values(JSON.parse(prevSavedAnswer));
  const prevSavedAnswerKeys = Object.keys(JSON.parse(prevSavedAnswer));

  return (
    prevSavedAnswerKeys.every((key) => questions.some(({ questionId }) => questionId === key)) &&
    prevSavedAnswerValues.some((value) => value !== '')
  );
}
