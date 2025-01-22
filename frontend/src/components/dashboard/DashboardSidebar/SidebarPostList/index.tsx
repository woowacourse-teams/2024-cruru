import { Link } from 'react-router-dom';

import type { IconType } from 'react-icons';
import { GrDocumentLocked, GrDocumentTime, GrDocumentUser } from 'react-icons/gr';

import { routes } from '@router/path';
import type { RecruitmentStatusObject } from '@utils/compareTime';
import { RecruitmentPost } from '../types';
import SidebarLinkContent from '../SidebarLinkContent';

import S from '../style';

interface SidebarPostListProps {
  isSidebarOpen: boolean;
  posts: RecruitmentPost[];
  title: string;
  onResetTab: () => void;
}

export default function SidebarPostList({ isSidebarOpen, posts, title, onResetTab }: SidebarPostListProps) {
  if (posts.length === 0) return null;

  const PostIconObj: Record<RecruitmentStatusObject['status'], IconType> = {
    Pending: GrDocumentTime,
    Ongoing: GrDocumentUser,
    Closed: GrDocumentLocked,
  };

  return (
    <S.SidebarItemGroup>
      <S.ContentSubTitle>{isSidebarOpen ? title : <S.Circle />}</S.ContentSubTitle>
      {posts.map((post) => {
        const PostIcon = PostIconObj[post.status.status];

        return (
          <S.SidebarItem
            isSidebarOpen={isSidebarOpen}
            key={post.applyFormId}
          >
            <Link
              to={routes.dashboard.post({
                dashboardId: String(post.dashboardId),
                applyFormId: post.applyFormId,
              })}
              onClick={onResetTab}
            >
              <SidebarLinkContent
                icon={
                  <PostIcon
                    size={16}
                    strokeWidth={4}
                  />
                }
                text={post.text}
                isSelected={post.isSelected}
                isSidebarOpen={isSidebarOpen}
              />
            </Link>
          </S.SidebarItem>
        );
      })}
    </S.SidebarItemGroup>
  );
}
