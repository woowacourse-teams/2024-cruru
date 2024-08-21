import { RecruitmentStatusType } from '@customTypes/recruitment';
import { getCleanDateString } from '@utils/formatDate';

interface GetRecruitmentStatusProps {
  startDate: string;
  endDate: string;
}

export function useGetRecruitmentStatus({ startDate, endDate }: GetRecruitmentStatusProps): RecruitmentStatusType {
  const currentDate = getCleanDateString();

  const currentDatePlus24Hours = new Date(currentDate);
  currentDatePlus24Hours.setHours(currentDate.getHours() + 24);

  if (getCleanDateString(startDate) > currentDate) return 'planned';
  if (getCleanDateString(endDate) < currentDatePlus24Hours) return 'closed';
  return 'inProgress';
}
