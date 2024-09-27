import dashboardApis from '@api/domain/dashboard';
import type { Club } from '@customTypes/dashboard';
import QUERY_KEYS from '@hooks/queryKeys';
import useClubId from '@hooks/service/useClubId';
import { useQuery } from '@tanstack/react-query';

export default function useGetDashboards() {
  const clubId = useClubId().getClubId() || '';

  const { data, error, isLoading } = useQuery<Club>({
    queryKey: [QUERY_KEYS.DASHBOARD, clubId],
    queryFn: () => dashboardApis.get({ clubId }),
    enabled: !!clubId,
  });

  return { data, error, isLoading };
}
