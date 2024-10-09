import { RecruitmentStatusType } from '@customTypes/recruitment';
import { getCleanDateString } from './formatDate';

interface GetRecruitmentStatusProps {
  startDate: string;
  endDate: string;
}

export interface RecruitmentStatusObject {
  status: RecruitmentStatusType;
  isPending: boolean;
  isOngoing: boolean;
  isClosed: boolean;
}

export function getTimeStatus({ startDate, endDate }: GetRecruitmentStatusProps): RecruitmentStatusObject {
  const currentDate = getCleanDateString();

  // TODO: 시간 지정 기능이 추가될 시 +24 로직은 사라져야 합니다.
  const endDatePlus24Hours = getCleanDateString(endDate);
  endDatePlus24Hours.setHours(endDatePlus24Hours.getHours() + 24);

  if (getCleanDateString(startDate) > currentDate) {
    return { status: 'Pending', isPending: true, isOngoing: false, isClosed: false };
  }
  if (currentDate < endDatePlus24Hours) {
    return { status: 'Ongoing', isPending: false, isOngoing: true, isClosed: false };
  }
  return { status: 'Closed', isPending: false, isOngoing: false, isClosed: true };
}
