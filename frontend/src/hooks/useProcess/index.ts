import { useQuery } from '@tanstack/react-query';

import type { Process, ProcessFilterOptions, ProcessResponse, ProcessSortOption } from '@customTypes/process';

import processApis from '@api/domain/process';
import QUERY_KEYS from '@hooks/queryKeys';
import { routes } from '@router/path';
import { DOMAIN_URL } from '../../constants/constants';

export interface SimpleProcess {
  processName: string;
  processId: number;
}

interface UseProcessProps {
  applyFormId: string;
  dashboardId: string;
  sortOption?: ProcessSortOption;
  filterOption?: ProcessFilterOptions;
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
}

export default function useProcess({
  dashboardId,
  applyFormId,
  sortOption,
  filterOption,
}: UseProcessProps): UseProcessReturn {
  const { data, error, isLoading } = useQuery<ProcessResponse>({
    queryKey: [
      QUERY_KEYS.DASHBOARD,
      dashboardId,
      applyFormId,
      sortOption ?? 'defaultSortOption',
      filterOption ?? 'defaultFilterOption',
    ],
    queryFn: () =>
      processApis.get({
        dashboardId,
        ...(sortOption && { [sortOption]: 'ASC' }),
        ...(filterOption && { filterOption }),
      }),
  });

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
  };
}
