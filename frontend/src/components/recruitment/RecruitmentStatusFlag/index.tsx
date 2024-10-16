import { RecruitmentStatusType } from '@customTypes/recruitment';
import { getTimeStatus } from '@utils/compareTime';
import S from './style';

interface RecruitmentStatusFlagProps {
  startDate: string;
  endDate: string;
}

const RECRUITMENT_STATUS: Record<RecruitmentStatusType, string> = {
  Pending: '모집 예정',
  Ongoing: '모집 진행 중',
  Closed: '모집 마감',
};

export default function RecruitmentStatusFlag({ startDate, endDate }: RecruitmentStatusFlagProps) {
  const { status } = getTimeStatus({ startDate, endDate });

  return <S.FlagContainer status={status}>{RECRUITMENT_STATUS[status]}</S.FlagContainer>;
}
