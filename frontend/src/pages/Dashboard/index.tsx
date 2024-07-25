import { useState } from 'react';
import { SpecificApplicantIdProvider } from '@contexts/SpecificApplicnatIdContext';
import KanbanBoard from '@components/dashboard/KanbanBoard';
import DashboardTab, { DashboardMenus } from '@components/dashboard/DashboardTab';
import ProcessManageBoard from '@components/processManagement/ProcessManageBoard';
import useProcess from '@hooks/useProcess';
import S from './style';

export default function Dashboard() {
  const [currentMenu, setCurrentMenu] = useState<DashboardMenus>('applicant');
  const { processes } = useProcess();

  const changeMenu = (e: React.MouseEvent<HTMLButtonElement>) => {
    const { name } = e.currentTarget;
    setCurrentMenu(name as DashboardMenus);
  };

  return (
    <S.AppContainer>
      <DashboardTab
        currentMenuKey={currentMenu}
        handleClickTabItem={changeMenu}
      />

      <S.DashboardPanel isVisible={currentMenu === 'applicant'}>
        <SpecificApplicantIdProvider>
          <KanbanBoard processes={processes} />
        </SpecificApplicantIdProvider>
      </S.DashboardPanel>

      <S.DashboardPanel isVisible={currentMenu === 'process'}>
        <ProcessManageBoard processes={processes} />
      </S.DashboardPanel>
    </S.AppContainer>
  );
}
