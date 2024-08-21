import { useEffect, useState } from 'react';
import type { Question, QuestionOptionValue } from '@customTypes/dashboard';
import { Question as QuestionData } from '@customTypes/apply';

import { applyQueries } from '@hooks/apply';
import { DEFAULT_QUESTIONS } from '@constants/constants';
import { useMutation, UseMutationResult, useQueryClient } from '@tanstack/react-query';
import questionApis from '@api/domain/question';
import QUERY_KEYS from '@hooks/queryKeys';

interface UseApplyManagementReturn {
  isLoading: boolean;
  applyState: Question[];
  modifyApplyQuestionsMutator: UseMutationResult<unknown, Error, void, unknown>;
  addQuestion: () => void;
  setQuestionTitle: (index: number) => (title: string) => void;
  setQuestionType: (index: number) => (type: Question['type']) => void;
  setQuestionOptions: (index: number) => (Options: QuestionOptionValue[]) => void;
  setQuestionRequiredToggle: (index: number) => () => void;
  setQuestionPrev: (index: number) => () => void;
  setQuestionNext: (index: number) => () => void;
  deleteQuestion: (index: number) => void;
}

interface UseApplyManagementProps {
  postId: string;
}

function getQuestions(data: QuestionData[] | undefined): Question[] {
  if (!data) return [];

  return data
    .sort((a, b) => a.orderIndex - b.orderIndex)
    .map((question) => ({
      id: Number(question.questionId),
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
  const { data, isLoading } = applyQueries.useGetApplyForm({ postId: postId ?? '' });
  const [applyState, setApplyState] = useState(getQuestions(data));
  const [uniqueId, setUniqueId] = useState(DEFAULT_QUESTIONS.length);
  const queryClient = useQueryClient();

  useEffect(() => {
    if (data && data.length > 0) {
      const newData = getQuestions(data);
      const newApplyState = [...DEFAULT_QUESTIONS, ...newData];
      setApplyState(newApplyState);
      setUniqueId(newApplyState.length);
    }
  }, [data]);

  const modifyApplyQuestionsMutator = useMutation({
    mutationFn: () =>
      questionApis.patch({
        applyformId: postId,
        questions: applyState.slice(DEFAULT_QUESTIONS.length).map((value, index) => ({
          orderIndex: index,
          type: value.type,
          label: value.question,
          choices: value.choices
            .filter(({ choice }) => !!choice)
            .map(({ choice, orderIndex }) => ({ label: choice, orderIndex })),
          required: value.required,
        })),
      }),
    onSuccess: async () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.RECRUITMENT_INFO, postId] });
      alert('지원서의 사전 질문 항목 수정에 성공했습니다.');
    },
    onError: () => {
      alert('지원서의 사전 질문 항목 수정에 실패했습니다.');
    },
  });

  const addQuestion = () => {
    setApplyState((prev) => [
      ...prev,
      { type: 'SHORT_ANSWER', question: '', choices: [], required: false, id: uniqueId },
    ]);

    setUniqueId(uniqueId + 1);
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
      if (type === 'SINGLE_CHOICE' || type === 'MULTIPLE_CHOICE') {
        questionsCopy[index].choices = [{ choice: '', orderIndex: 0 }];
      } else {
        questionsCopy[index].choices = [];
      }
      return questionsCopy;
    });
  };

  const setQuestionOptions = (index: number) => (options: QuestionOptionValue[]) => {
    setApplyState((prevState) => {
      const questionsCopy = [...prevState];
      questionsCopy[index].choices = options.map(({ choice }, i) => ({ choice, orderIndex: i }));
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
      if (index > DEFAULT_QUESTIONS.length) {
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
      if (index >= DEFAULT_QUESTIONS.length && index < prevState.length - 1) {
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
    if (index < DEFAULT_QUESTIONS.length) return;
    setApplyState((prevState) => {
      const newState = prevState.filter((_, i) => i !== index);
      return newState;
    });
  };

  return {
    isLoading,
    applyState,

    modifyApplyQuestionsMutator,

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
