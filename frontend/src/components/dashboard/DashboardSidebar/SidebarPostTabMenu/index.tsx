import type { IconType } from 'react-icons';
import { FiUsers, FiUserX, FiTrello, FiClipboard, FiEdit3 } from 'react-icons/fi';

import { DASHBOARD_TAB_MENUS } from '@constants/constants';
import { DashboardTabItems } from '@pages/DashboardLayout';
import SidebarLinkContent from '../SidebarLinkContent';

import S from '../style';

interface SidebarPostTabMenuProps {
  isSidebarOpen: boolean;
  currentMenu: DashboardTabItems;
  onMoveTab: (tab: DashboardTabItems) => void;
}

export default function SidebarPostTabMenu({ isSidebarOpen, currentMenu, onMoveTab }: SidebarPostTabMenuProps) {
  const TabIconObj: Record<DashboardTabItems, IconType> = {
    '지원자 관리': FiUsers,
    '불합격자 관리': FiUserX,
    '모집 과정 관리': FiTrello,
    '공고 편집': FiClipboard,
    '지원서 편집': FiEdit3,
  };

  return (
    <>
      <S.ContentSubTitle>{isSidebarOpen ? '공고 관리' : <S.Circle />}</S.ContentSubTitle>

      {Object.values(DASHBOARD_TAB_MENUS).map((label) => {
        const TabIcon = TabIconObj[label];

        return (
          <S.SidebarItem
            isSidebarOpen={isSidebarOpen}
            key={label}
          >
            <SidebarLinkContent
              icon={
                <TabIcon
                  size={16}
                  strokeWidth={2.5}
                />
              }
              text={label}
              isSelected={currentMenu === label}
              isSidebarOpen={isSidebarOpen}
              onClick={() => onMoveTab(label)}
            />
          </S.SidebarItem>
        );
      })}
    </>
  );
}
