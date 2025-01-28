import applyApis from '@api/domain/apply/apply';
import { ApplyRequestBody } from '@customTypes/apply';
import { useMutation, useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { getTimeStatus } from '@utils/compareTime';
import { routes } from '@router/path';
import { useToast } from '@contexts/ToastContext';
import QUERY_KEYS from './queryKeys';

const useGetRecruitmentInfo = ({ applyFormId }: { applyFormId: string }) => {
  const TEN_MINUTE = 1000 * 60 * 10;
  const queryObj = useQuery({
    queryKey: [QUERY_KEYS.RECRUITMENT_INFO, applyFormId],
    queryFn: () => applyApis.get({ applyFormId }),
    staleTime: TEN_MINUTE,
    gcTime: TEN_MINUTE,
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
  useApply: (applyFormId: string, title: string, resetStorage: () => void) => {
    const navigate = useNavigate();

    return useMutation({
      mutationFn: (params: { body: ApplyRequestBody }) => applyApis.apply({ ...params, applyFormId }),
      onSuccess: () => {
        resetStorage();
        navigate(routes.confirmApply({ applyFormId }), { state: { title } });
      },
    });
  },

  useDownloadCsv: () => {
    const { error } = useToast();
    return useMutation({
      mutationFn: ({ applyFormId }: { applyFormId: string }) => applyApis.exportAllApplicantsCsv({ applyFormId }),
      onSuccess: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `${new Date().toISOString().split('T')[0]}_지원자.csv`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      onError: (err) => {
        error(`CSV 다운로드 실패: ${err.message}`);
      },
    });
  },
};
