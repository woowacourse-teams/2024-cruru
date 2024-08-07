import { HiOutlineClock, HiOutlineShare } from 'react-icons/hi';
import formatDate from '@utils/formatDate';

import RecruitmentCardStat from '../RecruitmentCardStat';
import IconButton from '../../common/IconButton';
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
  onClickTitle: (dashboardId: number) => void;
  onClickShareButton: (dashboardId: number) => void;
}

const POST_STATS_KEY: Record<string, string> = {
  accept: '합격',
  fail: '불합격',
  inProgress: '평가 대상',
  total: '전체',
} as const;

export default function RecruitmentCard({
  dashboardId,
  title,
  postStats,
  endDate,
  onClickTitle,
  onClickShareButton,
}: RecruitmentCardProps) {
  const postStatsMap: [string, number][] = [
    [POST_STATS_KEY.total, postStats.total],
    [POST_STATS_KEY.inProgress, postStats.inProgress],
    [POST_STATS_KEY.fail, postStats.fail],
    [POST_STATS_KEY.accept, postStats.accept],
  ];

  return (
    <S.CardWrapper>
      <S.RecruitmentInfoContainer>
        <S.RecruitmentTitle onClick={() => onClickTitle(dashboardId)}>{title}</S.RecruitmentTitle>
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

        <IconButton
          size="sm"
          color="white"
          outline={false}
          onClick={() => onClickShareButton(dashboardId)}
        >
          <HiOutlineShare />
        </IconButton>
      </S.RecruitmentResultContainer>
    </S.CardWrapper>
  );
}
