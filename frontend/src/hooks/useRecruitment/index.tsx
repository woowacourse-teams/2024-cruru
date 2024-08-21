import { RecruitmentStatusType } from '@customTypes/recruitment';
import { getTimeStatus } from '@utils/compareTime';

interface GetRecruitmentStatusProps {
  startDate: string;
  endDate: string;
}

export function useGetRecruitmentStatus({ startDate, endDate }: GetRecruitmentStatusProps): RecruitmentStatusType {
  const returnNumber = getTimeStatus({ startDate, endDate });
  if (returnNumber === 0) return 'planned';
  if (returnNumber === 1) return 'inProgress';
  return 'closed';
}
