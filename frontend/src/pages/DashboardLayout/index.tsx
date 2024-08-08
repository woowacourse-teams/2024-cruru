import useGetDashboards from '@hooks/useGetDashboards';
import DashboardSidebar from '@components/dashboard/DashboardSidebar';
import { Outlet, useParams } from 'react-router-dom';
import S from './style';

export default function DashboardLayout() {
  const { dashboardId, postId } = useParams() as { dashboardId: string; postId: string };
  const { data, isLoading } = useGetDashboards({ dashboardId });

  if (isLoading) return <div>Loading...</div>;
  if (!data) return <div>something wrong</div>;

  const titleList = data.dashboards.map(({ title, dashboardId: postId2 }) => ({
    text: title,
    isSelected: !!postId && postId === postId2,
    postId: Number(postId2),
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
