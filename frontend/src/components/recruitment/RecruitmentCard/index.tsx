import { HiOutlineClock } from 'react-icons/hi';
import { RecruitmentStatusType } from '@customTypes/recruitment';
import formatDate from '@utils/formatDate';
import { getTimeStatus } from '@utils/compareTime';

import RecruitmentCardStat from '../RecruitmentCardStat';
import S from './style';

interface RecruitmentStats {
  accept: number;
  fail: number;
  inProgress: number;
  total: number;
}

interface RecruitmentCardProps {
  dashboardId: number;
  title: string;
  postStats: RecruitmentStats;
  startDate: string;
  endDate: string;
  onClick: (dashboardId: number) => void;
}

const POST_STATS_KEY: Record<string, string> = {
  accept: '합격',
  fail: '불합격',
  inProgress: '평가 대상',
  total: '전체',
} as const;

const RECRUITMENT_STATUS: Record<RecruitmentStatusType, string> = {
  Pending: '모집 예정',
  Ongoing: '모집 진행 중',
  Closed: '모집 마감',
};

export default function RecruitmentCard({
  dashboardId,
  title,
  postStats,
  startDate,
  endDate,
  onClick,
}: RecruitmentCardProps) {
  const { status } = getTimeStatus({ startDate, endDate });

  const postStatsMap: [string, number][] = [
    [POST_STATS_KEY.total, postStats.total],
    [POST_STATS_KEY.inProgress, postStats.inProgress],
    [POST_STATS_KEY.fail, postStats.fail],
    [POST_STATS_KEY.accept, postStats.accept],
  ];

  return (
    <S.CardWrapper onClick={() => onClick(dashboardId)}>
      <S.RecruitmentInfoContainer>
        <S.RecruitmentTitle>{title}</S.RecruitmentTitle>
        <S.RecruitmentStatusFlag status={status}>{RECRUITMENT_STATUS[status]}</S.RecruitmentStatusFlag>
        <S.EndDateContainer>
          <HiOutlineClock size="1.6rem" />
          <span>{formatDate(endDate)}</span>
        </S.EndDateContainer>
      </S.RecruitmentInfoContainer>

      <S.RecruitmentResultContainer>
        <S.PostStatsContainer>
          {postStatsMap.map(([label, value]) => (
            <RecruitmentCardStat
              key={`${dashboardId}-${label}`}
              label={label}
              number={value}
              isTotalStats={label === POST_STATS_KEY.total}
            />
          ))}
        </S.PostStatsContainer>
      </S.RecruitmentResultContainer>
    </S.CardWrapper>
  );
}
