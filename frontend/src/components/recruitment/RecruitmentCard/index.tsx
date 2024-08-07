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

export type RecruitmentStatusType = 'planned' | 'inProgress' | 'closed';

const POST_STATS_KEY: Record<string, string> = {
  accept: '합격',
  fail: '불합격',
  inProgress: '평가 대상',
  total: '전체',
} as const;

const RECRUITMENT_STATUS: Record<RecruitmentStatusType, string> = {
  planned: '모집 예정',
  inProgress: '모집 진행 중',
  closed: '모집 마감',
};

export default function RecruitmentCard({ dashboardId, title, postStats, endDate, onClick }: RecruitmentCardProps) {
  const postStatsMap: [string, number][] = [
    [POST_STATS_KEY.total, postStats.total],
    [POST_STATS_KEY.inProgress, postStats.inProgress],
    [POST_STATS_KEY.fail, postStats.fail],
    [POST_STATS_KEY.accept, postStats.accept],
  ];

  const getRecruitmentStatusFlag = () => {
    const presentTime = new Date();
    const endDateTime = new Date(endDate);

    // TODO:
    // 현재 API 스펙 상으로는 startDate가 넘어오지 않는 상태여서, '모집 예정' Flag를 표시할 수 없습니다.
    // 일단 현재로서는 '진행 중'과 '마감' Flag만 표시하고, 추후 API 업데이트시 아래에 해당 내용을 적용하기로 합니다.
    // - 작성자 : 아르, 작성일 : 24/08/07

    const status: RecruitmentStatusType = presentTime >= endDateTime ? 'closed' : 'inProgress';
    const statusString = RECRUITMENT_STATUS[status];

    return <S.RecruitmentStatusFlag status={status}>{statusString}</S.RecruitmentStatusFlag>;
  };

  return (
    <S.CardWrapper onClick={() => onClick(dashboardId)}>
      <S.RecruitmentInfoContainer>
        <S.RecruitmentTitle>{title}</S.RecruitmentTitle>
        {getRecruitmentStatusFlag()}
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
