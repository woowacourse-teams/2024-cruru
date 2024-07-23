import applicantApis from '@api/applicant';
import { SpecificApplicant } from '@customTypes/applicant';
import QUERY_KEYS from '@hooks/queryKeys';
import { useQuery } from '@tanstack/react-query';

interface UseSpecificApplicantProps {
  applicantId: number;
}

export default function useSpecificApplicant({ applicantId }: UseSpecificApplicantProps) {
  const { data, error, isLoading } = useQuery<SpecificApplicant>({
    queryKey: [QUERY_KEYS.APPLICANT, applicantId],
    queryFn: () => applicantApis.get({ applicantId }),
  });

  return {
    data,
    error,
    isLoading,
  };
}
