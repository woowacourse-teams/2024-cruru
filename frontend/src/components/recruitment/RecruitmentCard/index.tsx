import { HiOutlineClock } from 'react-icons/hi';
import formatDate from '@utils/formatDate';

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
  endDate: string;
  onClick: (dashboardId: number) => void;
}

const POST_STATS_KEY: Record<string, string> = {
  accept: '합격',
  fail: '불합격',
  inProgress: '평가 대상',
  total: '전체',
} as const;

export default function RecruitmentCard({ dashboardId, title, postStats, endDate, onClick }: RecruitmentCardProps) {
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
