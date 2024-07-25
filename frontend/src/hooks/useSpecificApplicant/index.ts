import applicantApis from '@api/applicant';
import { DASHBOARD_ID } from '@constants/constants';
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

  useRejectApplicant: () => {
    const queryClient = useQueryClient();

    return useMutation({
      mutationFn: ({ applicantId }: { applicantId: number }) => applicantApis.reject({ applicantId }),
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, DASHBOARD_ID] });
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
