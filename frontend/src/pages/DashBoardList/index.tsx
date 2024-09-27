import RecruitmentCard from '@components/recruitment/RecruitmentCard';
import useGetDashboards from '@hooks/useGetDashboards';
import { useNavigate } from 'react-router-dom';
import { routes } from '@router/path';
import S from './style';

export default function DashboardList() {
  const { data } = useGetDashboards();
  const navigate = useNavigate();

  const handleCardClick = (dashboardId: string, applyFormId: string) => {
    navigate(routes.dashboard.post({ dashboardId, applyFormId }));
  };

  return (
    <S.Container>
      <S.Title>{data?.clubName}</S.Title>
      <S.CardGrid>
        {data?.dashboards.map((dashboard) => (
          <RecruitmentCard
            key={`${dashboard.dashboardId}-${dashboard.applyFormId}`}
            dashboardId={dashboard.dashboardId}
            title={dashboard.title}
            postStats={dashboard.stats}
            startDate={dashboard.startDate}
            endDate={dashboard.endDate}
            onClick={() => handleCardClick(dashboard.dashboardId, dashboard.applyFormId)}
          />
        ))}
        <S.AddCard onClick={() => navigate(routes.dashboard.create())}>
          <div>+</div>
          <span>새 공고 추가</span>
        </S.AddCard>
      </S.CardGrid>
    </S.Container>
  );
}
