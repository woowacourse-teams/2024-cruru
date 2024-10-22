import { Suspense, useState } from 'react';

import DashboardSidebar from '@components/dashboard/DashboardSidebar';
import LoadingPage from '@pages/LoadingPage';

import useGetDashboards from '@hooks/useGetDashboards';
import useElementRect from '@hooks/useElementRect';

import { Outlet, useParams } from 'react-router-dom';
import { getTimeStatus } from '@utils/compareTime';
import S from './style';

export default function DashboardLayout() {
  const { applyFormId: currentPostId } = useParams();
  const { data } = useGetDashboards();
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);
  const [ref, rect] = useElementRect();

  const applyFormList = data?.dashboards.map(({ title, dashboardId, applyFormId, startDate, endDate }) => ({
    text: title,
    isSelected: !!currentPostId && currentPostId === String(applyFormId),
    status: getTimeStatus({ startDate, endDate }),
    applyFormId,
    dashboardId,
  }));

  const handleToggleSidebar = () => {
    if (isSidebarOpen) setIsSidebarOpen(false);
    if (!isSidebarOpen) setIsSidebarOpen(true);
  };

  return (
    <S.Layout>
      <S.Sidebar ref={ref}>
        <DashboardSidebar
          sidebarStyle={{ isSidebarOpen, onClickSidebarToggle: handleToggleSidebar }}
          options={applyFormList}
        />
      </S.Sidebar>

      <Suspense fallback={<LoadingPage />}>
        <S.MainContainer sidebarWidth={rect?.width}>
          <Outlet />
        </S.MainContainer>
      </Suspense>
    </S.Layout>
  );
}
