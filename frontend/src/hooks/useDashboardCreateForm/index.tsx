import dashboardApis from '@api/dashboard';
import { CLUB_ID } from '@constants/constants';
import type { Question, RecruitmentInfoState, StepState } from '@customTypes/dashboard';
import { useMutation, UseMutationResult } from '@tanstack/react-query';
import { useState } from 'react';

interface Option {
  value: string;
}
interface UseDashboardCreateFormReturn {
  stepState: StepState;
  nextStep: () => void;

  recruitmentInfoState: RecruitmentInfoState;
  setRecruitmentInfoState: React.Dispatch<React.SetStateAction<RecruitmentInfoState>>;
  applyState: Question[];

  addQuestion: () => void;
  setQuestionTitle: (index: number) => (title: string) => void;
  setQuestionOptions: (index: number) => (Options: Option[]) => void;
  setQuestionType: (index: number) => (type: Question['type']) => void;
  setQuestionPrev: (index: number) => () => void;
  setQuestionNext: (index: number) => () => void;
  deleteQuestion: (index: number) => void;

  submitMutator: UseMutationResult<unknown, Error, void, unknown>;
}

const initialRecruitmentInfoState: RecruitmentInfoState = {
  startDate: '',
  endDate: '',
  title: '',
  postingContent: '',
};

const initialApplyState: Question[] = [
  { type: 'SHORT_ANSWER', question: '이름', choices: [] },
  { type: 'SHORT_ANSWER', question: '이메일', choices: [] },
  { type: 'SHORT_ANSWER', question: '전화번호', choices: [] },
];

export default function useDashboardCreateForm(): UseDashboardCreateFormReturn {
  const [stepState, setStepState] = useState<StepState>('recruitmentForm');
  const [recruitmentInfoState, setRecruitmentInfoState] = useState<RecruitmentInfoState>(initialRecruitmentInfoState);
  const [applyState, setApplyState] = useState<Question[]>(initialApplyState);

  const nextStep = () => {
    if (stepState === 'recruitmentForm') setStepState('applyForm');
    if (stepState === 'applyForm') setStepState('finished');
  };

  const addQuestion = () => {
    setApplyState((prev) => [...prev, { type: 'SHORT_ANSWER', question: '', choices: [] }]);
  };

  const setQuestionTitle = (index: number) => (string: string) => {
    setApplyState((prevState) => {
      const questionsCopy = [...prevState];
      questionsCopy[index].question = string;
      return questionsCopy;
    });
  };

  const setQuestionType = (index: number) => (type: Question['type']) => {
    setApplyState((prevState) => {
      const questionsCopy = [...prevState];
      questionsCopy[index].type = type;
      questionsCopy[index].choices = [];
      return questionsCopy;
    });
  };

  const setQuestionOptions = (index: number) => (options: Option[]) => {
    setApplyState((prevState) => {
      const questionsCopy = [...prevState];
      questionsCopy[index].choices = options.map(({ value }, i) => ({ choice: value, order_index: i }));
      return questionsCopy;
    });
  };

  const setQuestionPrev = (index: number) => () => {
    setApplyState((prevState) => {
      if (index > 3) {
        const questionsCopy = [...prevState];
        const temp = questionsCopy[index];
        questionsCopy[index] = questionsCopy[index - 1];
        questionsCopy[index - 1] = temp;
        return questionsCopy;
      }
      return prevState;
    });
  };

  const setQuestionNext = (index: number) => () => {
    setApplyState((prevState) => {
      if (index >= 3 && index < prevState.length - 1) {
        const questionsCopy = [...prevState];
        const temp = questionsCopy[index];
        questionsCopy[index] = questionsCopy[index + 1];
        questionsCopy[index + 1] = temp;
        return questionsCopy;
      }
      return prevState;
    });
  };

  const deleteQuestion = (index: number) => {
    if (index < 3) return;
    setApplyState((prevState) => prevState.filter((_, i) => i !== index));
  };

  const submitMutator = useMutation({
    mutationFn: () =>
      dashboardApis.create({
        clubId: CLUB_ID,
        dashboardFormInfo: {
          ...recruitmentInfoState,
          questions: applyState,
        },
      }),
  });

  return {
    stepState,
    nextStep,

    recruitmentInfoState,
    setRecruitmentInfoState,
    applyState,

    addQuestion,
    setQuestionTitle,
    setQuestionOptions,
    setQuestionType,
    setQuestionPrev,
    setQuestionNext,
    deleteQuestion,

    submitMutator,
  };
}
