import applicantApis from '@api/domain/applicant';
import { useToast } from '@contexts/ToastContext';
import QUERY_KEYS from '@hooks/queryKeys';
import { useMutation, useQueryClient } from '@tanstack/react-query';

const applicantsReject = {
  useRejectApplicants: ({ dashboardId, applyFormId }: { dashboardId: string; applyFormId: string }) => {
    const queryClient = useQueryClient();
    const toast = useToast();

    return useMutation({
      mutationFn: ({ applicantIds }: { applicantIds: number[] }) => applicantApis.rejectApplicants({ applicantIds }),
      onSuccess: () => {
        toast.success('불합격 처리 되었습니다.');
        queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, applyFormId] });
      },
    });
  },

  useUnrejectApplicants: ({ dashboardId, applyFormId }: { dashboardId: string; applyFormId: string }) => {
    const queryClient = useQueryClient();
    const toast = useToast();

    return useMutation({
      mutationFn: ({ applicantIds }: { applicantIds: number[] }) => applicantApis.unrejectApplicants({ applicantIds }),
      onSuccess: () => {
        toast.success('재검토 대상으로 변경되었습니다.');
        queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, applyFormId] });
      },
    });
  },
};

export default applicantsReject;
