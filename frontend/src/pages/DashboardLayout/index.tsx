import { useRef, useState } from 'react';

import DashboardSidebar from '@components/dashboard/DashboardSidebar';
import IconButton from '@components/_common/atoms/IconButton';
import useGetDashboards from '@hooks/useGetDashboards';
import { HiChevronDoubleLeft, HiOutlineMenu } from 'react-icons/hi';

import { Outlet, useParams } from 'react-router-dom';
import S from './style';

export default function DashboardLayout() {
  const { applyFormId: currentPostId } = useParams();
  const { data, isLoading } = useGetDashboards();
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);
  const sidebarRef = useRef<HTMLDivElement>(null);

  if (isLoading) return <div>Loading...</div>;
  if (!data) return <div>something wrong</div>;

  const titleList = data.dashboards.map(({ title, dashboardId, applyFormId }) => ({
    text: title,
    isSelected: !!currentPostId && currentPostId === applyFormId,
    applyFormId,
    dashboardId,
  }));

  const handleToggleSidebar = () => {
    if (isSidebarOpen) setIsSidebarOpen(false);
    if (!isSidebarOpen) setIsSidebarOpen(true);
  };

  return (
    <S.Layout>
      <S.SidebarContainer>
        {isSidebarOpen && (
          <S.Sidebar ref={sidebarRef}>
            <DashboardSidebar options={titleList} />
          </S.Sidebar>
        )}

        <S.SidebarController isSidebarOpen={isSidebarOpen}>
          <S.ToggleButton>
            <IconButton
              size="sm"
              outline={false}
              onClick={handleToggleSidebar}
            >
              {isSidebarOpen ? <HiChevronDoubleLeft /> : <HiOutlineMenu />}
            </IconButton>
          </S.ToggleButton>
        </S.SidebarController>
      </S.SidebarContainer>

      <S.MainContainer isSidebarOpen={isSidebarOpen}>
        <Outlet />
      </S.MainContainer>
    </S.Layout>
  );
}
