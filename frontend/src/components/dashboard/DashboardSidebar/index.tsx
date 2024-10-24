import { useMemo } from 'react';

import Logo from '@assets/images/logo.svg';
import { routes } from '@router/path';
import { Link, useLocation } from 'react-router-dom';

import type { RecruitmentStatusObject } from '@utils/compareTime';

import { HiChevronDoubleLeft, HiOutlineHome } from 'react-icons/hi2';
import { HiOutlineMenu } from 'react-icons/hi';
import { GrDocumentLocked, GrDocumentTime, GrDocumentUser } from 'react-icons/gr';
import { FiUsers, FiUserX, FiTrello, FiClipboard, FiEdit3 } from 'react-icons/fi';
import type { IconType } from 'react-icons';
import type { DashboardTabItems } from '@pages/DashboardLayout';

import IconButton from '@components/_common/atoms/IconButton';
import { DASHBOARD_TAB_MENUS } from '@constants/constants';
import LogoutButton from './LogoutButton';

import S from './style';

interface Option {
  text: string;
  isSelected: boolean;
  applyFormId: string;
  dashboardId: number;
  status: RecruitmentStatusObject;
}

interface SidebarStyle {
  isSidebarOpen: boolean;
  onClickSidebarToggle: () => void;
}

interface DashboardSidebarProps {
  sidebarStyle: SidebarStyle;
  options?: Option[];
  isDashboard: boolean;
  currentMenu: DashboardTabItems;
  onMoveTab: (tab: DashboardTabItems) => void;
  onResetTab: () => void;
}

export default function DashboardSidebar({
  sidebarStyle,
  options,
  isDashboard,
  currentMenu,
  onMoveTab,
  onResetTab,
}: DashboardSidebarProps) {
  const pendingPosts = useMemo(() => options?.filter(({ status }) => status.isPending), [options]);
  const onGoingPosts = useMemo(() => options?.filter(({ status }) => status.isOngoing), [options]);
  const closedPosts = useMemo(() => options?.filter(({ status }) => status.isClosed), [options]);

  const sidebarContentList = [
    { title: '모집 예정 공고 목록', posts: pendingPosts },
    { title: '진행 중 공고 목록', posts: onGoingPosts },
    { title: '마감 된 공고 목록', posts: closedPosts },
  ];

  const location = useLocation();

  const IconObj: Record<RecruitmentStatusObject['status'], IconType> = {
    Pending: GrDocumentTime,
    Ongoing: GrDocumentUser,
    Closed: GrDocumentLocked,
  };

  const TabIconObj: Record<DashboardTabItems, IconType> = {
    '지원자 관리': FiUsers,
    '불합격자 관리': FiUserX,
    '모집 과정 관리': FiTrello,
    '공고 편집': FiClipboard,
    '지원서 편집': FiEdit3,
  };

  return (
    <S.Container isSidebarOpen={sidebarStyle.isSidebarOpen}>
      <S.SidebarHeader>
        {sidebarStyle.isSidebarOpen && (
          <Link to={routes.dashboard.list()}>
            <S.Logo
              src={Logo}
              alt="크루루 로고"
            />
          </Link>
        )}

        <IconButton
          size="sm"
          outline={false}
          onClick={sidebarStyle.onClickSidebarToggle}
        >
          <S.SidebarToggleIcon>
            {sidebarStyle.isSidebarOpen ? (
              <HiChevronDoubleLeft
                size={24}
                strokeWidth={0.8}
              />
            ) : (
              <HiOutlineMenu
                size={24}
                strokeWidth={2.4}
              />
            )}
          </S.SidebarToggleIcon>
        </IconButton>
      </S.SidebarHeader>

      <S.SidebarNav>
        <S.Contents>
          <S.SidebarItem isSidebarOpen={sidebarStyle.isSidebarOpen}>
            <Link to={routes.dashboard.list()}>
              <S.SidebarItemLink
                isSelected={location.pathname === routes.dashboard.list()}
                isSidebarOpen={sidebarStyle.isSidebarOpen}
              >
                <S.IconContainer>
                  <HiOutlineHome
                    size={22}
                    strokeWidth={2}
                  />
                </S.IconContainer>
                {sidebarStyle.isSidebarOpen && <S.SidebarItemTextHeader>모집 공고</S.SidebarItemTextHeader>}
              </S.SidebarItemLink>
            </Link>
            {!sidebarStyle.isSidebarOpen && <div className="sidebar-tooltip">모집 공고</div>}
          </S.SidebarItem>

          {!!options?.length && <S.Divider />}

          {isDashboard && (
            <>
              <S.ContentSubTitle>{sidebarStyle.isSidebarOpen ? '공고 관리' : <S.Circle />}</S.ContentSubTitle>
              {Object.values(DASHBOARD_TAB_MENUS).map((label) => {
                const Icon = TabIconObj[label];

                return (
                  /* eslint-disable react/jsx-indent */
                  <S.SidebarItem
                    isSidebarOpen={sidebarStyle.isSidebarOpen}
                    key={label}
                  >
                    <S.SidebarItemLink
                      isSelected={currentMenu === label}
                      isSidebarOpen={sidebarStyle.isSidebarOpen}
                      onClick={() => onMoveTab(label)}
                    >
                      <S.IconContainer>
                        <Icon
                          size={16}
                          strokeWidth={2.5}
                        />
                      </S.IconContainer>
                      {sidebarStyle.isSidebarOpen && <S.SidebarItemText>{label}</S.SidebarItemText>}
                    </S.SidebarItemLink>
                    {!sidebarStyle.isSidebarOpen && <div className="sidebar-tooltip">{label}</div>}
                  </S.SidebarItem>
                );
              })}
              <S.Divider />
            </>
          )}

          <S.SidebarScrollBox>
            {sidebarContentList.map(({ title, posts }) => {
              if (posts?.length === 0) return null;
              return (
                <S.SidebarItemGroup key={title}>
                  <S.ContentSubTitle>{sidebarStyle.isSidebarOpen ? title : <S.Circle />}</S.ContentSubTitle>
                  {posts?.map(({ text, isSelected, applyFormId, dashboardId, status }) => {
                    const Icon = IconObj[status.status];
                    return (
                      <S.SidebarItem
                        isSidebarOpen={sidebarStyle.isSidebarOpen}
                        key={applyFormId}
                      >
                        <Link
                          to={routes.dashboard.post({ dashboardId: String(dashboardId), applyFormId })}
                          onClick={onResetTab}
                        >
                          <S.SidebarItemLink
                            isSelected={isSelected}
                            isSidebarOpen={sidebarStyle.isSidebarOpen}
                          >
                            <S.IconContainer>
                              <Icon
                                size={16}
                                strokeWidth={4}
                              />
                            </S.IconContainer>
                            {sidebarStyle.isSidebarOpen && <S.SidebarItemText>{text}</S.SidebarItemText>}
                          </S.SidebarItemLink>
                        </Link>
                        {!sidebarStyle.isSidebarOpen && <div className="sidebar-tooltip">{text}</div>}
                      </S.SidebarItem>
                    );
                  })}
                </S.SidebarItemGroup>
              );
            })}
          </S.SidebarScrollBox>
        </S.Contents>
      </S.SidebarNav>

      {sidebarStyle.isSidebarOpen && <LogoutButton />}
    </S.Container>
  );
}
