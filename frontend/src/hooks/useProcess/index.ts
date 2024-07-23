import { useQuery } from '@tanstack/react-query';

import type { Process } from '@customTypes/process';
import { getProcesses } from '@api/process';

import { DASHBOARD_ID } from '@constants/constants';

interface SimpleProcess {
  processName: string;
  processId: number;
}

interface UseProcessReturn {
  processes: Process[];
  processList: SimpleProcess[];
  error: Error | null;
  isLoading: boolean;
}

export default function useProcess(): UseProcessReturn {
  const { data, error, isLoading } = useQuery<{ processes: Process[] }>({
    queryKey: ['dashboard', DASHBOARD_ID],
    queryFn: () => getProcesses({ id: DASHBOARD_ID }),
  });

  const processes = data?.processes || [];

  const processList = processes.map((p) => ({ processName: p.name, processId: p.processId }));

  return {
    processes,
    processList,
    error,
    isLoading,
  };
}
