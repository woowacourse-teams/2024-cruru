import emailApis from '@api/domain/email';
import QUERY_KEYS from '@hooks/queryKeys';
import useClubId from '@hooks/service/useClubId';
import { useQuery } from '@tanstack/react-query';

interface UseGetEmailHistoryProps {
  applicantId: number;
}

export const createEmailHistoryQueryKey = (clubId: string, applicantId: number) => [
  QUERY_KEYS.EMAIL_HISTORY,
  clubId,
  applicantId,
];

export default function useGetEmailHistory({ applicantId }: UseGetEmailHistoryProps) {
  const clubId = useClubId().getClubId() || '';

  const {
    data: { emailHistoryResponses: emailHistory },
  } = useQuery({
    queryKey: createEmailHistoryQueryKey(clubId, applicantId),
    queryFn: () => emailApis.history({ clubId, applicantId }),
    initialData: { emailHistoryResponses: [] },
  });

  return {
    emailHistory,
  };
}
