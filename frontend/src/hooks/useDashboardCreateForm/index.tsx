import dashboardApis from '@api/dashboard';
import type { Question, QuestionOptionValue, RecruitmentInfoState, StepState } from '@customTypes/dashboard';
import { useMutation, UseMutationResult } from '@tanstack/react-query';
import { useState } from 'react';

interface Option {
  value: string;
}

interface FinishResJson {
  postUrl: string;
  postId: string;
}
interface UseDashboardCreateFormReturn {
  stepState: StepState;
  prevStep: () => void;
  nextStep: (dashboardId?: string) => void;

  recruitmentInfoState: RecruitmentInfoState;
  setRecruitmentInfoState: React.Dispatch<React.SetStateAction<RecruitmentInfoState>>;
  applyState: Question[];

  addQuestion: () => void;
  setQuestionTitle: (index: number) => (title: string) => void;
  setQuestionType: (index: number) => (type: Question['type']) => void;
  setQuestionOptions: (index: number) => (Options: QuestionOptionValue[]) => void;
  setQuestionRequiredToggle: (index: number) => () => void;
  setQuestionPrev: (index: number) => () => void;
  setQuestionNext: (index: number) => () => void;
  deleteQuestion: (index: number) => void;

  submitMutator: UseMutationResult<
    Response,
    Error,
    {
      clubId: string;
    },
    unknown
  >;
  finishResJson: FinishResJson | null;
}

const initialRecruitmentInfoState: RecruitmentInfoState = {
  startDate: '',
  endDate: '',
  title: '',
  postingContent: '',
};

const initialApplyState: Question[] = [
  { type: 'SHORT_ANSWER', question: '이름', choices: [], required: true },
  { type: 'SHORT_ANSWER', question: '이메일', choices: [], required: true },
  { type: 'SHORT_ANSWER', question: '전화번호', choices: [], required: true },
];

export default function useDashboardCreateForm(): UseDashboardCreateFormReturn {
  const [stepState, setStepState] = useState<StepState>('recruitmentForm');
  const [recruitmentInfoState, setRecruitmentInfoState] = useState<RecruitmentInfoState>(initialRecruitmentInfoState);
  const [applyState, setApplyState] = useState<Question[]>(initialApplyState);
  const [finishResJson, setFinishResJson] = useState<FinishResJson | null>(null);

  const submitMutator = useMutation({
    mutationFn: ({ clubId }: { clubId: string }) =>
      dashboardApis.create({
        clubId,
        dashboardFormInfo: {
          ...recruitmentInfoState,
          questions: applyState,
        },
      }),
    onSuccess: async (data) => {
      setStepState('finished');
      const json = await data?.json();
      setFinishResJson(json);
    },
  });

  const prevStep = () => {
    if (stepState === 'applyForm') setStepState('recruitmentForm');
  };

  const nextStep = (dashboardId?: string) => {
    if (stepState === 'recruitmentForm') setStepState('applyForm');
    if (stepState === 'applyForm') {
      if (dashboardId) {
        submitMutator.mutate({ clubId: dashboardId });
      }
    }
  };

  const addQuestion = () => {
    setApplyState((prev) => [...prev, { type: 'SHORT_ANSWER', question: '', choices: [], required: false }]);
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

  const setQuestionOptions = (index: number) => (options: QuestionOptionValue[]) => {
    setApplyState((prevState) => {
      const questionsCopy = [...prevState];
      questionsCopy[index].choices = options.map(({ value }, i) => ({ choice: value, orderIndex: i }));
      return questionsCopy;
    });
  };

  const setQuestionRequiredToggle = (index: number) => () => {
    setApplyState((prevState) => {
      const questionsCopy = [...prevState];
      questionsCopy[index].required = !prevState[index].required;
      return questionsCopy;
    });
  };

  const setQuestionPrev = (index: number) => () => {
    setApplyState((prevState) => {
      if (index > initialApplyState.length) {
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
      if (index >= initialApplyState.length && index < prevState.length - 1) {
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
    if (index < initialApplyState.length) return;
    setApplyState((prevState) => prevState.filter((_, i) => i !== index));
  };

  return {
    stepState,
    prevStep,
    nextStep,

    recruitmentInfoState,
    setRecruitmentInfoState,
    applyState,

    addQuestion,
    setQuestionTitle,
    setQuestionType,
    setQuestionOptions,
    setQuestionRequiredToggle,
    setQuestionPrev,
    setQuestionNext,
    deleteQuestion,

    submitMutator,
    finishResJson,
  };
}
