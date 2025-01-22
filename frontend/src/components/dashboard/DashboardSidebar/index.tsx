import { useMemo } from 'react';
import { useLocation } from 'react-router-dom';

import { routes } from '@router/path';

import type { DashboardTabItems } from '@pages/DashboardLayout';
import type { RecruitmentPost } from './types';

import SidebarHeader from './SidebarHeader';
import SidebarHomeButton from './SidebarHomeButton';
import SidebarPostTabMenu from './SidebarPostTabMenu';
import SidebarPostList from './SidebarPostList';
import LogoutButton from './LogoutButton';

import S from './style';

interface SidebarStyle {
  isSidebarOpen: boolean;
  onClickSidebarToggle: () => void;
}

interface DashboardSidebarProps {
  sidebarStyle: SidebarStyle;
  posts?: RecruitmentPost[];
  isDashboard: boolean;
  currentMenu: DashboardTabItems;
  onMoveTab: (tab: DashboardTabItems) => void;
  onResetTab: () => void;
}

export default function DashboardSidebar({
  sidebarStyle,
  posts,
  isDashboard,
  currentMenu,
  onMoveTab,
  onResetTab,
}: DashboardSidebarProps) {
  const { pathname } = useLocation();

  const pendingPosts = useMemo(() => posts?.filter(({ status }) => status.isPending ?? []) ?? [], [posts]);
  const onGoingPosts = useMemo(() => posts?.filter(({ status }) => status.isOngoing ?? []) ?? [], [posts]);
  const closedPosts = useMemo(() => posts?.filter(({ status }) => status.isClosed ?? []) ?? [], [posts]);

  const sidebarPostList = [
    { title: '모집 예정 공고 목록', posts: pendingPosts },
    { title: '진행 중 공고 목록', posts: onGoingPosts },
    { title: '마감 된 공고 목록', posts: closedPosts },
  ];

  return (
    <S.Container isSidebarOpen={sidebarStyle.isSidebarOpen}>
      <SidebarHeader
        isSidebarOpen={sidebarStyle.isSidebarOpen}
        onToggleSidebar={sidebarStyle.onClickSidebarToggle}
      />

      <S.SidebarNav>
        <S.Contents>
          <SidebarHomeButton
            isSidebarOpen={sidebarStyle.isSidebarOpen}
            isSelected={pathname === routes.dashboard.list()}
          />

          {!!posts?.length && <S.Divider />}

          {isDashboard && (
            <>
              <SidebarPostTabMenu
                isSidebarOpen={sidebarStyle.isSidebarOpen}
                currentMenu={currentMenu}
                onMoveTab={onMoveTab}
              />
              <S.Divider />
            </>
          )}

          <S.SidebarScrollBox>
            {sidebarPostList.map(({ title, posts: postsPerStatus }) => (
              <SidebarPostList
                key={title}
                isSidebarOpen={sidebarStyle.isSidebarOpen}
                title={title}
                posts={postsPerStatus}
                onResetTab={onResetTab}
              />
            ))}
          </S.SidebarScrollBox>
        </S.Contents>
      </S.SidebarNav>

      {sidebarStyle.isSidebarOpen && <LogoutButton />}
    </S.Container>
  );
}
