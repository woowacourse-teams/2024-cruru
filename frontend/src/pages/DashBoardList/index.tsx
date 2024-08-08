import RecruitmentCard from '@components/recruitment/RecruitmentCard';
import { useNavigate, useParams } from 'react-router-dom';
import useGetDashboards from '@hooks/useGetDashboards';
import S from './style';

export default function DashboardList() {
  const { dashboardId } = useParams() as { dashboardId: string };
  const { data } = useGetDashboards({ dashboardId });
  const navigate = useNavigate();

  const handleCardClick = (postId: number) => {
    navigate(`/dashboard/${dashboardId}/${postId}`);
  };

  return (
    <S.Container>
      <S.Title>{data?.clubName}</S.Title>
      <S.CardGrid>
        {data?.dashboards.map((dashboard) => (
          <RecruitmentCard
            key={dashboard.dashboardId}
            // TODO: dashboardId -> postId로 변경
            dashboardId={Number(dashboard.dashboardId)}
            title={dashboard.title}
            postStats={dashboard.stats}
            startDate={dashboard.startDate}
            endDate={dashboard.endDate}
            onClick={handleCardClick}
          />
        ))}
        <S.AddCard onClick={() => navigate(`/dashboard/${dashboardId}/create`)}>
          <div>+</div>
          <span>새 공고 추가</span>
        </S.AddCard>
      </S.CardGrid>
    </S.Container>
  );
}
