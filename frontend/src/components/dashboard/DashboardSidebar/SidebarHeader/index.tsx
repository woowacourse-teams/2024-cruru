import { Link } from 'react-router-dom';

import { routes } from '@router/path';
import IconButton from '@components/_common/atoms/IconButton';
import Tooltip from '@components/_common/molecules/Tooltip';
import Logo from '@assets/images/logo.svg';

import { HiChevronDoubleLeft } from 'react-icons/hi2';
import { HiOutlineMenu } from 'react-icons/hi';
import S from './style';

interface SidebarHeaderProps {
  isSidebarOpen: boolean;
  onToggleSidebar: () => void;
}

export default function SidebarHeader({ isSidebarOpen, onToggleSidebar }: SidebarHeaderProps) {
  const sidebarOpenButton = (
    <Tooltip
      content="사이드바 열기"
      placement="right"
      distanceFromTarget={20}
    >
      <HiOutlineMenu
        size={24}
        strokeWidth={2.4}
      />
    </Tooltip>
  );

  const sidebarCloseButton = (
    <HiChevronDoubleLeft
      size={24}
      strokeWidth={0.8}
    />
  );

  return (
    <S.SidebarHeader>
      {isSidebarOpen && (
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
        onClick={onToggleSidebar}
      >
        <S.SidebarToggleIcon>{isSidebarOpen ? sidebarCloseButton : sidebarOpenButton}</S.SidebarToggleIcon>
      </IconButton>
    </S.SidebarHeader>
  );
}
