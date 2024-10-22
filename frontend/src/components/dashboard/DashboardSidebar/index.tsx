import { useMemo } from 'react';

import Logo from '@assets/images/logo.svg';
import { routes } from '@router/path';
import { Link, useLocation } from 'react-router-dom';

import type { RecruitmentStatusObject } from '@utils/compareTime';

import { Fragment } from 'react/jsx-runtime';
import { HiChevronDoubleLeft, HiOutlineHome } from 'react-icons/hi2';
import { HiOutlineMenu } from 'react-icons/hi';
import { GrDocumentLocked, GrDocumentTime, GrDocumentUser } from 'react-icons/gr';
import type { IconType } from 'react-icons';

import IconButton from '@components/_common/atoms/IconButton';
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
}

export default function DashboardSidebar({ sidebarStyle, options }: DashboardSidebarProps) {
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

      <nav>
        <S.Contents>
          <S.SidebarItem>
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
          </S.SidebarItem>

          {!!options?.length && <S.Divider />}

          {sidebarContentList.map(({ title, posts }) => {
            if (posts?.length === 0) return null;
            return (
              <Fragment key={title}>
                <S.ContentSubTitle>{sidebarStyle.isSidebarOpen ? title : <S.Circle />}</S.ContentSubTitle>
                {posts?.map(
                  ({ text, isSelected, applyFormId: applyFormIdNum, dashboardId: dashboardIdNum, status }) => {
                    const Icon = IconObj[status.status];
                    const dashboardId = String(dashboardIdNum);
                    const applyFormId = String(applyFormIdNum);
                    return (
                      <S.SidebarItem key={applyFormId}>
                        <Link to={routes.dashboard.post({ dashboardId, applyFormId })}>
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
                      </S.SidebarItem>
                    );
                  },
                )}
              </Fragment>
            );
          })}
        </S.Contents>
      </nav>

      {sidebarStyle.isSidebarOpen && <LogoutButton />}
    </S.Container>
  );
}
