import DashboardSidebar from '@components/dashboard/DashboardSidebar';
import useGetDashboards from '@hooks/useGetDashboards';
import { Outlet, useParams } from 'react-router-dom';
import S from './style';

export default function DashboardLayout() {
  const { applyFormId: currentPostId } = useParams();
  const { data, isLoading } = useGetDashboards();

  if (isLoading) return <div>Loading...</div>;
  if (!data) return <div>something wrong</div>;

  const titleList = data.dashboards.map(({ title, dashboardId, applyFormId }) => ({
    text: title,
    isSelected: !!currentPostId && currentPostId === applyFormId,
    applyFormId,
    dashboardId,
  }));

  return (
    <S.LayoutBg>
      <S.Layout>
        <DashboardSidebar options={titleList} />

        <S.MainContainer>
          <Outlet />
        </S.MainContainer>
      </S.Layout>
    </S.LayoutBg>
  );
}
