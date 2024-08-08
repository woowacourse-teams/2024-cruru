import { useQuery } from '@tanstack/react-query';

import type { Process } from '@customTypes/process';

import processApis from '@api/process';
import QUERY_KEYS from '@hooks/queryKeys';

interface SimpleProcess {
  processName: string;
  processId: number;
}

interface UseProcessProps {
  dashboardId: string;
  postId: string;
}

interface UseProcessReturn {
  processes: Process[];
  processList: SimpleProcess[];
  error: Error | null;
  isLoading: boolean;
}

export default function useProcess({ dashboardId, postId }: UseProcessProps): UseProcessReturn {
  const { data, error, isLoading } = useQuery<{ processes: Process[] }>({
    queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, postId],
    queryFn: () => processApis.get({ id: postId }),
  });

  const processes = data?.processes || [];

  const processList = processes.map((p) => ({ processName: p.name, processId: p.processId }));
  return {
    processes: processes.sort((processA, processB) => processA.orderIndex - processB.orderIndex),
    processList,
    error,
    isLoading,
  };
}
