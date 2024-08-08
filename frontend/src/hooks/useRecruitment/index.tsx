import { RecruitmentStatusType } from '@customTypes/recruitment';
import { getCleanDateString } from '@utils/formatDate';

interface GetRecruitmentStatusProps {
  startDate: string;
  endDate: string;
}

export function useGetRecruitmentStatus({ startDate, endDate }: GetRecruitmentStatusProps): RecruitmentStatusType {
  const currentDate = getCleanDateString();

  if (getCleanDateString(startDate) > currentDate) return 'planned';
  if (getCleanDateString(endDate) <= currentDate) return 'closed';
  return 'inProgress';
}
