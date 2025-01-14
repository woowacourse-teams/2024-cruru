import emailApis from '@api/domain/email';
import useClubId from '@hooks/service/useClubId';
import { useQuery } from '@tanstack/react-query';

interface UseGetEmailHistoryProps {
  applicantId: number;
}

export default function useGetEmailHistory({ applicantId }: UseGetEmailHistoryProps) {
  const clubId = useClubId().getClubId() || '';

  const { data: { emailResponses: emailHistory } = { emailResponses: [] } } = useQuery({
    queryKey: ['emailHistory', clubId, applicantId],
    queryFn: () => emailApis.history({ clubId, applicantId }),
  });

  return {
    emailHistory,
  };
}
