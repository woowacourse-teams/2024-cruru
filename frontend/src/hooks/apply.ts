import applyApis from '@api/domain/apply/apply';
import { ApplyRequestBody } from '@customTypes/apply';
import { useMutation, useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { getTimeStatus } from '@utils/compareTime';
import { routes } from '@router/path';
import QUERY_KEYS from './queryKeys';

const useGetRecruitmentInfo = ({ applyFormId }: { applyFormId: string }) => {
  const queryObj = useQuery({
    queryKey: [QUERY_KEYS.RECRUITMENT_INFO, applyFormId],
    queryFn: () => applyApis.get({ applyFormId }),
    staleTime: 1000 * 60 * 10, // 10 minutes
  });

  return queryObj;
};

export const applyQueries = {
  useGetRecruitmentPost: ({ applyFormId }: { applyFormId: string }) => {
    const { data, ...restQueryObj } = useGetRecruitmentInfo({ applyFormId });

    const { startDate, endDate } = data?.recruitmentPost ?? { startDate: '', endDate: '' };
    const isClosed = !getTimeStatus({ startDate, endDate }).isOngoing;

    return {
      isClosed,
      data: data?.recruitmentPost,
      ...restQueryObj,
    };
  },

  useGetApplyForm: ({ applyFormId }: { applyFormId: string }) => {
    const { data, ...restQueryObj } = useGetRecruitmentInfo({ applyFormId });

    return {
      data: data?.applyForm.questions,
      ...restQueryObj,
    };
  },
};

export const applyMutations = {
  useApply: (applyFormId: string, title: string) => {
    const navigate = useNavigate();

    return useMutation({
      mutationFn: (params: { body: ApplyRequestBody }) => applyApis.apply({ ...params, applyFormId }),
      onSuccess: () => {
        navigate(routes.confirmApply({ applyFormId }), { state: { title } });
      },
    });
  },
};
