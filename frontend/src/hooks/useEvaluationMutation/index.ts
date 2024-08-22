import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import evaluationApis from '@api/domain/evaluation';
import QUERY_KEYS from '@hooks/queryKeys';

interface UseEvaluationMutationParams {
  processId: number;
  applicantId: number;
}

interface MutationParams extends UseEvaluationMutationParams {
  score: string;
  content: string;
}

export default function useEvaluationMutation({ processId, applicantId }: UseEvaluationMutationParams) {
  const queryClient = useQueryClient();
  const { dashboardId, postId } = useParams() as { dashboardId: string; postId: string };

  const invalidateQueries = () => {
    queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.EVALUATION, processId, applicantId] });
    queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, postId] });
  };

  return useMutation({
    mutationFn: (params: MutationParams) => evaluationApis.create(params),
    onSuccess: () => {
      invalidateQueries();
    },
  });
}
