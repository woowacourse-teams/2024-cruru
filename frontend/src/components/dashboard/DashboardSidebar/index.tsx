import Logo from '@assets/images/logo.svg';
import { routes } from '@router/path';
import { Link, useLocation } from 'react-router-dom';

import type { RecruitmentStatusObject } from '@utils/compareTime';

import { Fragment } from 'react/jsx-runtime';
import { HiChevronDoubleLeft, HiOutlineHome } from 'react-icons/hi2';
import { HiOutlineMenu } from 'react-icons/hi';
import { GrDocumentTime, GrDocumentUser, GrDocumentVerified } from 'react-icons/gr';

import IconButton from '@components/_common/atoms/IconButton';
import LogoutButton from './LogoutButton';

import S from './style';

interface Option {
  text: string;
  isSelected: boolean;
  applyFormId: string;
  dashboardId: string;
  status: RecruitmentStatusObject;
}

interface SidebarStyle {
  isSidebarOpen: boolean;
  onClickSidebarToggle: () => void;
}

interface DashboardSidebarProps {
  sidebarStyle: SidebarStyle;
  options: Option[];
}

export default function DashboardSidebar({ sidebarStyle, options }: DashboardSidebarProps) {
  const pendingPosts = options.filter(({ status }) => status.isPending);
  const onGoingPosts = options.filter(({ status }) => status.isOngoing);
  const closedPosts = options.filter(({ status }) => status.isClosed);

  const sidebarContentList = [
    { title: '모집 예정 공고 목록', posts: pendingPosts },
    { title: '진행 중 공고 목록', posts: onGoingPosts },
    { title: '마감 된 공고 목록', posts: closedPosts },
  ];
  const location = useLocation();

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
          {sidebarStyle.isSidebarOpen ? <HiChevronDoubleLeft /> : <HiOutlineMenu />}
        </IconButton>
      </S.SidebarHeader>

      <nav>
        <S.Contents>
          <S.SidebarItem>
            <Link to={routes.dashboard.list()}>
              <S.SidebarItemLink isSelected={location.pathname === routes.dashboard.list()}>
                <HiOutlineHome
                  size={24}
                  strokeWidth={2.4}
                />
                {sidebarStyle.isSidebarOpen && <S.SidebarItemText>모집 공고</S.SidebarItemText>}
              </S.SidebarItemLink>
            </Link>
          </S.SidebarItem>

          <S.Divider />

          {sidebarContentList.map(({ title, posts }) => {
            if (posts.length === 0) return;
            return (
              <Fragment key={title}>
                <S.ContentSubTitle>{sidebarStyle.isSidebarOpen ? title : <S.Circle />}</S.ContentSubTitle>
                {posts.map(({ text, isSelected, applyFormId, dashboardId, status }) => (
                  <S.SidebarItem key={applyFormId}>
                    <Link to={routes.dashboard.post({ dashboardId, applyFormId })}>
                      <S.SidebarItemLink isSelected={isSelected}>
                        {status.isPending ? (
                          <GrDocumentTime
                            size={24}
                            strokeWidth={2}
                          />
                        ) : status.isOngoing ? (
                          <GrDocumentUser
                            size={24}
                            strokeWidth={2}
                          />
                        ) : status.isClosed ? (
                          <GrDocumentVerified
                            size={24}
                            strokeWidth={2}
                          />
                        ) : null}
                        {sidebarStyle.isSidebarOpen && <S.SidebarItemText>{text}</S.SidebarItemText>}
                      </S.SidebarItemLink>
                    </Link>
                  </S.SidebarItem>
                ))}
              </Fragment>
            );
          })}
        </S.Contents>
      </nav>

      {sidebarStyle.isSidebarOpen && <LogoutButton />}
    </S.Container>
  );
}
