import { Suspense, useEffect, useState } from 'react';

import DashboardSidebar from '@components/dashboard/DashboardSidebar';
import useTab from '@components/_common/molecules/Tab/useTab';
import LoadingPage from '@pages/LoadingPage';

import useGetDashboards from '@hooks/useGetDashboards';
import useElementRect from '@hooks/useElementRect';

import { Outlet, useParams } from 'react-router-dom';
import { getTimeStatus } from '@utils/compareTime';
import S from './style';

export type DashboardTabItems = '지원자 관리' | '모집 과정 관리' | '불합격자 관리' | '공고 편집' | '지원서 편집';

export default function DashboardLayout() {
  const { applyFormId: currentPostId } = useParams();
  const { currentMenu, moveTabByParam, resetTab } = useTab<DashboardTabItems>({ defaultValue: '지원자 관리' });

  const { data } = useGetDashboards();
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);
  const [ref, rect] = useElementRect();

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

  useEffect(() => {
    if (!currentPostId) {
      resetTab();
    }
  }, [currentPostId, resetTab]);

  return (
    <S.Layout>
      <S.Sidebar ref={ref}>
        <DashboardSidebar
          sidebarStyle={{ isSidebarOpen, onClickSidebarToggle: handleToggleSidebar }}
          options={applyFormList}
          isDashboard={!!currentPostId}
          currentMenu={currentMenu}
          onMoveTab={(tab) => moveTabByParam(tab)}
          onResetTab={() => resetTab()}
        />
      </S.Sidebar>

      <Suspense fallback={<LoadingPage />}>
        <S.MainContainer sidebarWidth={rect?.width}>
          <Outlet context={{ currentMenu, moveTabByParam }} />
        </S.MainContainer>
      </Suspense>
    </S.Layout>
  );
}
