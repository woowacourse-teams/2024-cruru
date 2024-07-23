import { getSpecificApplicant } from '@api/applicant';
import QUERY_KEYS from '@hooks/queryKeys';
import { useQuery } from '@tanstack/react-query';

interface UseSpecificApplicantProps {
  applicantId: number;
}

interface SpecificApplicant {
  applicantId: number;
  createdAt: string;
  name: string;
  email: string;
  phone: string;
  processName: string;
}

interface UseSpecificApplicantReturn {
  data: SpecificApplicant | undefined;
  error: Error | null;
  isLoading: boolean;
}

export default function useSpecificApplicant({ applicantId }: UseSpecificApplicantProps): UseSpecificApplicantReturn {
  const { data, error, isLoading } = useQuery<SpecificApplicant>({
    queryKey: [QUERY_KEYS.APPLICANT, applicantId],
    queryFn: () => getSpecificApplicant({ applicantId }),
  });

  return {
    data,
    error,
    isLoading,
  };
}
