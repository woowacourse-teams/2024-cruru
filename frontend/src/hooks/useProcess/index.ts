import { useQuery } from '@tanstack/react-query';

import type { EvaluationStatus, Process, ProcessResponse } from '@customTypes/process';

import processApis from '@api/domain/process';
import QUERY_KEYS from '@hooks/queryKeys';
import useSortApplicant from '@hooks/useProcess/useSortApplicant';
import { routes } from '@router/path';
import { DOMAIN_URL } from '../../constants/constants';
import useFilterApplicant, { RatingFilterType } from './useFilterApplicant';

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

const EVALUATION_STATUS: Record<RatingFilterType, EvaluationStatus> = {
  All: 'ALL',
  Pending: 'NOT_EVALUATED',
  InProgress: 'EVALUATED',
};

export default function useProcess({ dashboardId, applyFormId }: UseProcessProps): UseProcessReturn {
  const applicantSortDropdownProps = useSortApplicant();
  const ratingFilterProps = useFilterApplicant();

  const { data, error, isLoading } = useQuery<ProcessResponse>({
    // eslint-disable-next-line
    queryKey: [
      QUERY_KEYS.DASHBOARD,
      dashboardId,
      applyFormId,
      JSON.stringify(applicantSortDropdownProps.sortOption),
      JSON.stringify(ratingFilterProps.ratingRange),
      JSON.stringify(ratingFilterProps.ratingFilterType),
    ],
    queryFn: () =>
      processApis.get({
        dashboardId,
        ...applicantSortDropdownProps.sortOption,
        minScore: ratingFilterProps.ratingRange.min.toString(),
        maxScore: ratingFilterProps.ratingRange.max.toString(),
        evaluationStatus: EVALUATION_STATUS[ratingFilterProps.ratingFilterType],
      }),
  });

  const processes = data?.processes || [];

  const processList = processes.map((p) => ({ processName: p.name, processId: p.processId }));

  return {
    title: data?.title ?? '',
    postUrl: `${DOMAIN_URL}${routes.post({ applyFormId: data?.applyFormId ?? '' })}`,
    processes,
    processList,
    error,
    isLoading,
    startDate: data?.startDate ?? '0',
    endDate: data?.endDate ?? '0',
    applicantSortDropdownProps,
    ratingFilterProps,
  };
}
