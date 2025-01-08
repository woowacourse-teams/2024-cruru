import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';

// import { useToast } from '@contexts/ToastContext';

import evaluationApis from '@api/domain/evaluation';
import QUERY_KEYS from '@hooks/queryKeys';

interface DefaultMutationParams {
  processId: number;
  applicantId: number;
}

interface UseCreateEvaluationMutationParams extends DefaultMutationParams {
  closeOnSuccess: () => void;
}

interface CreateMutationParams extends DefaultMutationParams {
  evaluator: string;
  score: number;
  content: string;
}

function useEvaluationQueryInvalidation() {
  const queryClient = useQueryClient();
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };

  return (processId: number, applicantId: number) => {
    queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.EVALUATION, processId, applicantId] });
    queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, applyFormId] });
  };
}

export function useCreateEvaluationMutation({
  processId,
  applicantId,
  closeOnSuccess,
}: UseCreateEvaluationMutationParams) {
  const invalidateQueries = useEvaluationQueryInvalidation();
  /**
   * 지원자 상세 모달이 <dialog>로 구현되어, 모달이 toast 메시지를 덮는 문제가 남아있습니다.
   * 모달이 <div> 기반으로 재구현될 때까지 toast 메시지 코드를 주석 처리합니다.
   * - 25/01/08 아르
   */
  // const toast = useToast();

  return useMutation({
    mutationFn: (params: CreateMutationParams) => evaluationApis.create(params),
    onSuccess: () => {
      invalidateQueries(processId, applicantId);
      closeOnSuccess();
      // toast.success('지원자에 대한 평가가 등록되었습니다.');
    },
  });
}

export function useDeleteEvaluationMutation({ processId, applicantId }: DefaultMutationParams) {
  const invalidateQueries = useEvaluationQueryInvalidation();
  /**
   * 지원자 상세 모달이 <dialog>로 구현되어, 모달이 toast 메시지를 덮는 문제가 남아있습니다.
   * 모달이 <div> 기반으로 재구현될 때까지 toast 메시지 코드를 주석 처리합니다.
   * - 25/01/08 아르
   */
  // const toast = useToast();

  return useMutation({
    mutationFn: ({ evaluationId }: { evaluationId: number }) => evaluationApis.delete({ evaluationId }),
    onSuccess: () => {
      invalidateQueries(processId, applicantId);
      // toast.success('지원자에 대한 해당 평가가 삭제되었습니다.');
    },
  });
}
