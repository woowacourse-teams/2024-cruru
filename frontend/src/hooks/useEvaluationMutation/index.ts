import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
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

  return useMutation({
    mutationFn: (params: CreateMutationParams) => evaluationApis.create(params),
    onSuccess: () => {
      invalidateQueries(processId, applicantId);
      closeOnSuccess();
    },
  });
}

export function useDeleteEvaluationMutation({ processId, applicantId }: DefaultMutationParams) {
  const invalidateQueries = useEvaluationQueryInvalidation();

  return useMutation({
    mutationFn: ({ evaluationId }: { evaluationId: number }) => evaluationApis.delete({ evaluationId }),
    onSuccess: () => {
      invalidateQueries(processId, applicantId);
    },
  });
}
