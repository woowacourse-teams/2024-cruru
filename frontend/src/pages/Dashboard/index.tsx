import { useParams } from 'react-router-dom';

import ProcessManageBoard from '@components/processManagement/ProcessManageBoard';
import Tab from '@components/common/Tab';
import KanbanBoard from '@components/dashboard/KanbanBoard';

import useTab from '@components/common/Tab/useTab';
import useProcess from '@hooks/useProcess';

import { DASHBOARD_TAB_MENUS } from '@constants/constants';
import { SpecificApplicantIdProvider } from '@contexts/SpecificApplicnatIdContext';

import CopyToClipboard from '@components/common/CopyToClipboard';
import S from './style';

export type DashboardTabItems = '지원자 관리' | '모집 과정 관리';

export default function Dashboard() {
  const { dashboardId, postId } = useParams() as { dashboardId: string; postId: string };
  const { processes, isLoading, title, postUrl } = useProcess({ dashboardId, postId });

  const { currentMenu, moveTab } = useTab<DashboardTabItems>({ defaultValue: '지원자 관리' });

  if (isLoading) {
    // TODO: Suspense로 Refactoring
    return <div>Loading ...</div>;
  }

  return (
    <S.AppContainer>
      <S.Header>
        <S.Title>{title}</S.Title>

        <S.CopyWrapper>
          <CopyToClipboard
            url={postUrl}
            title={`${title} 공고`}
          />
        </S.CopyWrapper>
      </S.Header>

      <Tab>
        {Object.values(DASHBOARD_TAB_MENUS).map((label) => (
          <Tab.TabItem
            key={label}
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
        <ProcessManageBoard
          dashboardId={dashboardId}
          postId={postId}
          processes={processes}
        />
      </Tab.TabPanel>
    </S.AppContainer>
  );
}
