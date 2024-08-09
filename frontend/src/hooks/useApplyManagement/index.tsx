import { useEffect, useState } from 'react';
import type { Question, QuestionOptionValue } from '@customTypes/dashboard';
import { Question as QuestionData } from '@customTypes/apply';

import { applyQueries } from '@hooks/apply';

interface UseApplyManagementReturn {
  applyState: Question[];
  addQuestion: () => void;
  setQuestionTitle: (index: number) => (title: string) => void;
  setQuestionType: (index: number) => (type: Question['type']) => void;
  setQuestionOptions: (index: number) => (Options: QuestionOptionValue[]) => void;
  setQuestionRequiredToggle: (index: number) => () => void;
  setQuestionPrev: (index: number) => () => void;
  setQuestionNext: (index: number) => () => void;
  deleteQuestion: (index: number) => void;
}

const initialApplyState: Question[] = [
  { type: 'SHORT_ANSWER', question: '이름', choices: [], required: true },
  { type: 'SHORT_ANSWER', question: '이메일', choices: [], required: true },
  { type: 'SHORT_ANSWER', question: '전화번호', choices: [], required: true },
];

interface UseApplyManagementProps {
  postId: string;
}

function getQuestions(data: QuestionData[] | undefined): Question[] {
  if (!data) return [];

  return data
    .sort((a, b) => a.orderIndex - b.orderIndex)
    .map((question) => ({
      type: question.type,
      question: question.label,
      choices: question.choices.map((choice) => ({
        choice: choice.label,
        orderIndex: choice.orderIndex,
      })),
      required: question.required,
    }));
}

export default function useApplyManagement({ postId }: UseApplyManagementProps): UseApplyManagementReturn {
  const { data } = applyQueries.useGetApplyForm({ postId: postId ?? '' });
  const [applyState, setApplyState] = useState(getQuestions(data));

  useEffect(() => {
    if (data && data.length > 0) {
      setApplyState(() => {
        const newData = getQuestions(data);
        return [...initialApplyState, ...newData];
      });
    }
  }, [data]);

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
    setApplyState((prevState) => {
      const newState = prevState.filter((_, i) => i !== index);
      return newState;
    });
  };

  return {
    applyState,
    addQuestion,
    setQuestionTitle,
    setQuestionType,
    setQuestionOptions,
    setQuestionRequiredToggle,
    setQuestionPrev,
    setQuestionNext,
    deleteQuestion,
  };
}
