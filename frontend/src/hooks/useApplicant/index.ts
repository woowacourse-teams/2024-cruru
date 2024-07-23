import applicantApis from '@api/applicant';
import { DASHBOARD_ID } from '@constants/constants';
import QUERY_KEYS from '@hooks/queryKeys';
import { useMutation, useQueryClient } from '@tanstack/react-query';

export default function useApplicant({ applicantId }: { applicantId?: number }) {
  const queryClient = useQueryClient();

  const moveApplicantProcess = useMutation({
    mutationFn: ({ processId, applicants }: { processId: number; applicants: number[] }) =>
      applicantApis.move({ processId, applicants }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, DASHBOARD_ID] });
      if (applicantId) {
        queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.APPLICANT, applicantId] });
      }
    },
  });

  const rejectApplicant = useMutation({
    mutationFn: ({ applicantId }: { applicantId: number }) => applicantApis.reject({ applicantId }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, DASHBOARD_ID] });
    },
  });

  return {
    moveApplicantProcess,
    rejectApplicant,
  };
}
