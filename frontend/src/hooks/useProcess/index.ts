import { useQuery } from '@tanstack/react-query';

import type { Process } from '@customTypes/process';
import { getProcesses } from '@api/process';

interface IUseProcessReturn {
  processes: Process[];
  processNameList: string[];
  error: Error | null;
  isLoading: boolean;
}

export default function useProcess(): IUseProcessReturn {
  const ID = 1; // TODO: 수정해야합니다.

  const { data, error, isLoading } = useQuery<{ processes: Process[] }>({
    queryKey: ['dashboard', ID],
    queryFn: () => getProcesses({ id: ID }),
  });

  const processes = data?.processes || [];

  const processNameList = processes.map((p) => p.name);

  return {
    processes,
    processNameList,
    error,
    isLoading,
  };
}
