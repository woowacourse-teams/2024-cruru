/* eslint-disable no-trailing-spaces */
import { useParams } from 'react-router-dom';

import Tab from '@components/_common/molecules/Tab';
import ProcessBoard from '@components/dashboard/ProcessBoard';
import ApplyManagement from '@components/applyManagement';
import ProcessManageBoard from '@components/processManagement/ProcessManageBoard';
import PostManageBoard from '@components/postManagement/PostManageBoard';
import OpenInNewTab from '@components/_common/atoms/OpenInNewTab';
import CopyToClipboard from '@components/_common/atoms/CopyToClipboard';

import useTab from '@components/_common/molecules/Tab/useTab';
import useProcess from '@hooks/useProcess';

import { DASHBOARD_TAB_MENUS } from '@constants/constants';
import { SpecificApplicantIdProvider } from '@contexts/SpecificApplicnatIdContext';
import { SpecificProcessIdProvider } from '@contexts/SpecificProcessIdContext';
import { FloatingEmailFormProvider } from '@contexts/FloatingEmailFormContext';

import S from './style';

export type DashboardTabItems = '지원자 관리' | '모집 과정 관리' | '불합격자 관리' | '공고 관리' | '지원서 관리';

export default function Dashboard() {
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };
  const { processes, isLoading, title, postUrl } = useProcess({ dashboardId, applyFormId });

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
          <OpenInNewTab
            url={postUrl}
            title="공고로 이동"
          />
          <CopyToClipboard url={postUrl} />
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

      {/* TODO: [08.21-lurgi] 현재 모달이 여러개를 컨트롤 할 수 없는 관계로 새로 렌더링 합니다.
      추후에 Modal에 id값을 부여하여 여러개의 모달을 컨트롤 할 수 있게 변경해야합니다.
      파일 맨 첫줄 주석도 삭제해야합니다. */}

      <FloatingEmailFormProvider>
        <Tab.TabPanel isVisible={currentMenu === '지원자 관리'}>
          <SpecificApplicantIdProvider>
            <SpecificProcessIdProvider>
              <ProcessBoard processes={processes} />
            </SpecificProcessIdProvider>
          </SpecificApplicantIdProvider>
        </Tab.TabPanel>

        <Tab.TabPanel isVisible={currentMenu === '불합격자 관리'}>
          <SpecificApplicantIdProvider>
            <SpecificProcessIdProvider>
              <ProcessBoard
                processes={processes}
                showRejectedApplicant
              />
            </SpecificProcessIdProvider>
          </SpecificApplicantIdProvider>
        </Tab.TabPanel>
      </FloatingEmailFormProvider>

      <Tab.TabPanel isVisible={currentMenu === '모집 과정 관리'}>
        <ProcessManageBoard
          dashboardId={dashboardId}
          applyFormId={applyFormId}
          processes={processes}
        />
      </Tab.TabPanel>

      <Tab.TabPanel isVisible={currentMenu === '공고 관리'}>
        <PostManageBoard applyFormId={applyFormId} />
      </Tab.TabPanel>

      <Tab.TabPanel isVisible={currentMenu === '지원서 관리'}>
        <ApplyManagement />
      </Tab.TabPanel>
    </S.AppContainer>
  );
}
