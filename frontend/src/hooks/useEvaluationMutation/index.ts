import evaluationApis from '@api/evaluation';
import QUERY_KEYS from '@hooks/queryKeys';
import { useMutation, useQueryClient } from '@tanstack/react-query';

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
  const invalidateQueries = () => {
    queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.EVALUATION, processId, applicantId] });
  };

  return useMutation({
    mutationFn: (params: MutationParams) => evaluationApis.create(params),
    onSuccess: () => {
      invalidateQueries();
    },
    onError: () => {
      alert('지원자 평가 등록에 실패했습니다.');
    },
  });
}
