import { useState } from 'react';
import Tab from '@components/common/Tab';
import KanbanBoard from '@components/dashboard/KanbanBoard';
import ProcessManageBoard from '@components/processManagement/ProcessManageBoard';

import useProcess from '@hooks/useProcess';

import { DASHBOARD_TAB_MENUS } from '@constants/constants';
import { SpecificApplicantIdProvider } from '@contexts/SpecificApplicnatIdContext';

import S from './style';

export type DashboardTabItems = '지원자 관리' | '모집 과정 관리';

export default function Dashboard() {
  const { processes } = useProcess();

  const [currentMenu, setCurrentMenu] = useState<DashboardTabItems>('지원자 관리');

  const moveTab = (e: React.MouseEvent<HTMLButtonElement>) => {
    const { name } = e.currentTarget;
    setCurrentMenu(name as DashboardTabItems);
  };

  return (
    <S.AppContainer>
      <Tab>
        {Object.values(DASHBOARD_TAB_MENUS).map((label) => (
          <Tab.TabItem
            label={label}
            name={label}
            isActive={currentMenu === label}
            handleClickTabItem={moveTab}
          />
        ))}
      </Tab>

      <Tab.TabPanel isVisible={currentMenu === '지원자 관리'}>
        <SpecificApplicantIdProvider>
          <KanbanBoard processes={processes} />
        </SpecificApplicantIdProvider>
      </Tab.TabPanel>

      <Tab.TabPanel isVisible={currentMenu === '모집 과정 관리'}>
        <ProcessManageBoard processes={processes} />
      </Tab.TabPanel>
    </S.AppContainer>
  );
}
