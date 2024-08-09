import applicantApis from '@api/applicant';
import QUERY_KEYS from '@hooks/queryKeys';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';

export default function useApplicant({ applicantId }: { applicantId?: number }) {
  const queryClient = useQueryClient();
  const { dashboardId, postId } = useParams() as { dashboardId: string; postId: string };

  const moveApplicantProcess = useMutation({
    mutationFn: ({ processId, applicants }: { processId: number; applicants: number[] }) =>
      applicantApis.move({ processId, applicants }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, postId] });
      if (applicantId) {
        queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.APPLICANT, applicantId] });
      }
    },
  });

  return {
    moveApplicantProcess,
  };
}
