import emailApis from '@api/domain/email';
import QUERY_KEYS from '@hooks/queryKeys';
import useClubId from '@hooks/service/useClubId';
import { useQuery } from '@tanstack/react-query';

interface UseGetEmailHistoryProps {
  applicantId: number;
}

export default function useGetEmailHistory({ applicantId }: UseGetEmailHistoryProps) {
  const clubId = useClubId().getClubId() || '';

  const { data: { emailResponses: emailHistory } = { emailResponses: [] } } = useQuery({
    queryKey: [QUERY_KEYS.EMAIL_HISTORY, clubId, applicantId],
    queryFn: () => emailApis.history({ clubId, applicantId }),
  });

  return {
    emailHistory,
  };
}
