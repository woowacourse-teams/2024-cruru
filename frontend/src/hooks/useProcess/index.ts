import { useQuery } from '@tanstack/react-query';

import type { Process, ProcessResponse } from '@customTypes/process';

import processApis from '@api/domain/process';
import QUERY_KEYS from '@hooks/queryKeys';
import useSortApplicant from '@hooks/useProcess/useSortApplicant';
import { routes } from '@router/path';
import { useEffect } from 'react';
import { DOMAIN_URL } from '../../constants/constants';
import useFilterApplicant from './useFilterApplicant';

export interface SimpleProcess {
  processName: string;
  processId: number;
}

interface UseProcessProps {
  applyFormId: string;
  dashboardId: string;
}

interface UseProcessReturn {
  title: string;
  processes: Process[];
  processList: SimpleProcess[];
  error: Error | null;
  isLoading: boolean;
  postUrl: string;
  startDate: string;
  endDate: string;
  applicantSortDropdownProps: ReturnType<typeof useSortApplicant>;
  ratingFilterProps: ReturnType<typeof useFilterApplicant>;
}

export default function useProcess({ dashboardId, applyFormId }: UseProcessProps): UseProcessReturn {
  const applicantSortDropdownProps = useSortApplicant();
  const ratingFilterProps = useFilterApplicant();

  const { data, error, isLoading, refetch } = useQuery<ProcessResponse>({
    // [10.17-lesser]
    // sortOption을 queryKey에 추가하면 기존 요청까지 다시 보내게 되어
    // queryKey에 sortOption을 추가하지 않고, refetch로 재요청 보내도록 수정합니다.
    // eslint-disable-next-line
    queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, applyFormId],
    queryFn: () =>
      processApis.get({
        dashboardId,
        ...applicantSortDropdownProps.sortOption,
        minScore: ratingFilterProps.ratingRange.min.toString(),
        maxScore: ratingFilterProps.ratingRange.max.toString(),
        evaluationStatus: ratingFilterProps.ratingFilterType === 'All' ? 'ALL' : 'EVALUATED',
      }),
  });

  useEffect(() => {
    refetch();
  }, [applicantSortDropdownProps.sortOption, ratingFilterProps.ratingRange, ratingFilterProps.ratingFilterType]);

  const processes = data?.processes || [];

  const processList = processes.map((p) => ({ processName: p.name, processId: p.processId }));

  return {
    title: data?.title ?? '',
    postUrl: `${DOMAIN_URL}${routes.post({ applyFormId: data?.applyFormId ?? '' })}`,
    processes: processes.sort((processA, processB) => processA.orderIndex - processB.orderIndex),
    processList,
    error,
    isLoading,
    startDate: data?.startDate ?? '0',
    endDate: data?.endDate ?? '0',
    applicantSortDropdownProps,
    ratingFilterProps,
  };
}
