import { useQuery } from '@tanstack/react-query';

import type { Process, ProcessResponse } from '@customTypes/process';

import processApis from '@api/domain/process';
import QUERY_KEYS from '@hooks/queryKeys';
import { routes } from '@router/path';
import { DOMAIN_URL } from '../../constants/constants';

interface SimpleProcess {
  processName: string;
  processId: number;
}

interface UseProcessProps {
  dashboardId: string;
  applyFormId: string;
}

interface UseProcessReturn {
  title: string;
  processes: Process[];
  processList: SimpleProcess[];
  error: Error | null;
  isLoading: boolean;
  postUrl: string;
}

export default function useProcess({ dashboardId, applyFormId }: UseProcessProps): UseProcessReturn {
  const { data, error, isLoading } = useQuery<ProcessResponse>({
    queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, applyFormId],
    queryFn: () => processApis.get({ dashboardId }),
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
  };
}
