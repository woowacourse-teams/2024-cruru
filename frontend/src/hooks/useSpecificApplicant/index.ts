import applicantApis from '@api/domain/applicant';
import { useToast } from '@contexts/ToastContext';
import { ApplicantDetail, SpecificApplicant } from '@customTypes/applicant';
import QUERY_KEYS from '@hooks/queryKeys';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

interface UseSpecificApplicantProps {
  applicantId: number;
}

const specificApplicant = {
  useGetBaseInfo: ({ applicantId }: UseSpecificApplicantProps) =>
    useQuery<SpecificApplicant>({
      queryKey: [QUERY_KEYS.APPLICANT, applicantId],
      queryFn: () => applicantApis.get({ applicantId }),
    }),

  useRejectApplicant: ({ dashboardId, postId }: { dashboardId: string; postId: string }) => {
    const queryClient = useQueryClient();
    const toast = useToast();

    return useMutation({
      mutationFn: ({ applicantId }: { applicantId: number }) => applicantApis.reject({ applicantId }),
      onSuccess: () => {
        toast.success('불합격 처리 되었습니다.');
        queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, postId] });
      },
    });
  },

  useUnrejectApplicant: ({ dashboardId, postId }: { dashboardId: string; postId: string }) => {
    const queryClient = useQueryClient();
    const toast = useToast();

    return useMutation({
      mutationFn: ({ applicantId }: { applicantId: number }) => applicantApis.unreject({ applicantId }),
      onSuccess: () => {
        toast.success('부활하였습니다. 👻예토전생👻');
        queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, postId] });
      },
    });
  },

  useGetDetailInfo: ({ applicantId }: UseSpecificApplicantProps) =>
    useQuery<ApplicantDetail>({
      queryKey: [QUERY_KEYS.DETAIL_APPLICANT, applicantId],
      queryFn: () => applicantApis.getDetail({ applicantId }),
    }),
};

export default specificApplicant;
