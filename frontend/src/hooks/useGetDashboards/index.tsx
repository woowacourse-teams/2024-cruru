import dashboardApis from '@api/domain/dashboard';
import type { Club } from '@customTypes/dashboard';
import QUERY_KEYS from '@hooks/queryKeys';
import { useQuery } from '@tanstack/react-query';

interface UseGetDashboardsProps {
  dashboardId: string;
}

export default function useGetDashboards({ dashboardId }: UseGetDashboardsProps) {
  const { data, error, isLoading } = useQuery<Club>({
    queryKey: [QUERY_KEYS.DASHBOARD, dashboardId],
    queryFn: () => dashboardApis.get({ dashboardId }),
  });

  return { data, error, isLoading };
}
