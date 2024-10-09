import { useState } from 'react';

import DashboardSidebar from '@components/dashboard/DashboardSidebar';
import useGetDashboards from '@hooks/useGetDashboards';

import { Outlet, useParams } from 'react-router-dom';
import { getTimeStatus } from '@utils/compareTime';
import S from './style';

export default function DashboardLayout() {
  const { applyFormId: currentPostId } = useParams();
  const { data, isLoading } = useGetDashboards();
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);

  const applyFormList = data?.dashboards.map(({ title, dashboardId, applyFormId, startDate, endDate }) => ({
    text: title,
    isSelected: !!currentPostId && currentPostId === applyFormId,
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
      <S.Sidebar>
        {isLoading ? (
          <div>Loading...</div> // TODO: Suspense로 리팩토링
        ) : !applyFormList ? (
          <div>something wrong</div> // TODO: ErrorBoundary로 리팩터링
        ) : (
          <DashboardSidebar
            sidebarStyle={{ isSidebarOpen, onClickSidebarToggle: handleToggleSidebar }}
            options={applyFormList}
          />
        )}
      </S.Sidebar>

      <S.MainContainer isSidebarOpen={isSidebarOpen}>
        <Outlet />
      </S.MainContainer>
    </S.Layout>
  );
}
