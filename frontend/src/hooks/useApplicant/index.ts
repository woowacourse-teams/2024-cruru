import { moveApplicant } from '@api/applicant';
import { DASHBOARD_ID } from '@constants/constants';
import { useMutation, useQueryClient } from '@tanstack/react-query';

export default function useApplicant() {
  const queryClient = useQueryClient();

  const moveApplicantProcess = useMutation({
    mutationFn: ({ processId, applicants }: { processId: number; applicants: number[] }) =>
      moveApplicant({ processId, applicants }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['dashboard', DASHBOARD_ID] });
    },
  });

  return {
    moveApplicantProcess,
  };
}
