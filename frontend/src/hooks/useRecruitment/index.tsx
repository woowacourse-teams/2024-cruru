import { RecruitmentStatusType } from '@customTypes/recruitment';

interface GetRecruitmentStatusProps {
  startDate: string;
  endDate: string;
}

export function useGetRecruitmentStatus({ startDate, endDate }: GetRecruitmentStatusProps): RecruitmentStatusType {
  const currentDate = new Date();

  if (new Date(startDate) > currentDate) return 'planned';
  if (new Date(endDate) <= currentDate) return 'closed';
  return 'inProgress';
}
