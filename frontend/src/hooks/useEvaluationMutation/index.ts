import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import evaluationApis from '@api/domain/evaluation';
import QUERY_KEYS from '@hooks/queryKeys';

interface UseEvaluationMutationParams {
  processId: number;
  applicantId: number;
  closeOnSuccess: () => void;
}

interface MutationParams {
  processId: number;
  applicantId: number;
  score: number;
  content: string;
}

export default function useEvaluationMutation({ processId, applicantId, closeOnSuccess }: UseEvaluationMutationParams) {
  const queryClient = useQueryClient();
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };

  const invalidateQueries = () => {
    queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.EVALUATION, processId, applicantId] });
    queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, applyFormId] });
  };

  return useMutation({
    mutationFn: (params: MutationParams) => evaluationApis.create(params),
    onSuccess: () => {
      invalidateQueries();
      closeOnSuccess();
    },
  });
}
