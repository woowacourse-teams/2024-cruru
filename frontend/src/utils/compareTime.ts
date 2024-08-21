import { getCleanDateString } from '@utils/formatDate';

interface GetRecruitmentStatusProps {
  startDate: string;
  endDate: string;
}

export function getTimeStatus({ startDate, endDate }: GetRecruitmentStatusProps) {
  const currentDate = getCleanDateString();

  // TODO: 시간 지정 기능이 추가될 시 +24 로직은 사라져야 합니다.
  const endDatePlus24Hours = getCleanDateString(endDate);
  endDatePlus24Hours.setHours(endDatePlus24Hours.getHours() + 24);

  if (getCleanDateString(startDate) > currentDate) return 0;
  if (currentDate < endDatePlus24Hours) return 1;
  return 2;
}
