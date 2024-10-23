import applicantApis from '@api/domain/applicant';
import QUERY_KEYS from '@hooks/queryKeys';
import { useToast } from '@contexts/ToastContext';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';

export default function useApplicant({ applicantId }: { applicantId?: number }) {
  const queryClient = useQueryClient();
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };
  const toast = useToast();

  return useMutation({
    mutationFn: ({ processId, applicants }: { processId: number; applicants: number[] }) =>
      applicantApis.move({ processId, applicants }),

    onSuccess: (_, variables) => {
      const { processId } = variables;
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, applyFormId] });
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.EVALUATION, processId, applicantId] });
      if (applicantId) {
        queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.APPLICANT, applicantId] });
      }
      toast.success('지원자 단계가 이동되었습니다.');
    },
  });
}
