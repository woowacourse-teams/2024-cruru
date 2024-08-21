import applicantApis from '@api/domain/applicant';
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

    return useMutation({
      mutationFn: ({ applicantId }: { applicantId: number }) => applicantApis.reject({ applicantId }),
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, postId] });
      },
    });
  },

  useUnrejectApplicant: ({ dashboardId, postId }: { dashboardId: string; postId: string }) => {
    const queryClient = useQueryClient();

    return useMutation({
      mutationFn: ({ applicantId }: { applicantId: number }) => applicantApis.unreject({ applicantId }),
      onSuccess: () => {
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
