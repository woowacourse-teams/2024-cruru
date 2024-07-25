import { useQuery } from '@tanstack/react-query';
import { EvaluationResult } from '@customTypes/applicant';

import QUERY_KEYS from '@hooks/queryKeys';
import evaluationApis from '@api/evaluation';

interface UseEvaluationQueryParams {
  processId: number;
  applicantId: number;
}

export default function useEvaluationQuery({ processId, applicantId }: UseEvaluationQueryParams) {
  const { data, error, isLoading } = useQuery<{ evaluations: EvaluationResult[] }>({
    queryKey: [QUERY_KEYS.EVALUATION, processId, applicantId],
    queryFn: () => evaluationApis.get({ processId, applicantId }),
    enabled: !!processId && !!applicantId,
  });

  const evaluations = data?.evaluations || [];

  const evaluationList: EvaluationResult[] = evaluations.map((e) => ({
    evaluationId: e.evaluationId,
    evaluatorName: e.evaluatorName ?? undefined,
    score: e.score,
    content: e.content,
    createdAt: e.createdAt ?? undefined,
  }));

  return {
    evaluations,
    evaluationList,
    error,
    isLoading,
  };
}
