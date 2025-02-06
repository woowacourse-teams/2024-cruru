import { Link } from 'react-router-dom';

import { routes } from '@router/path';
import { HiOutlineHome } from 'react-icons/hi2';

import SidebarLinkContent from '../SidebarLinkContent';
import S from '../style';

interface SidebarHomeButtonProps {
  isSidebarOpen: boolean;
  isSelected: boolean;
}

export default function SidebarHomeButton({ isSidebarOpen, isSelected }: SidebarHomeButtonProps) {
  return (
    <S.SidebarItem isSidebarOpen={isSidebarOpen}>
      <Link to={routes.dashboard.list()}>
        <SidebarLinkContent
          icon={
            <HiOutlineHome
              size={22}
              strokeWidth={2}
            />
          }
          text="모집 공고"
          isSelected={isSelected}
          isSidebarOpen={isSidebarOpen}
        />
      </Link>
    </S.SidebarItem>
  );
}
